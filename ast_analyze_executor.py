import logging.config
# from ast_processor import AstProcessor
# from basic_info_listener import BasicInfoListener
import yaml
from antlr4 import FileStream, CommonTokenStream,Token # type: ignore
from JavaLexer import JavaLexer
import glob
import os

log_conf = './program/logs/log_conf.yaml'  

def main():
    logging.config.dictConfig(yaml.load(open(log_conf).read(),
                                        Loader=yaml.SafeLoader))
    logger = logging.getLogger('mainLogger')
    
    input_files = glob.glob("./program/input/*.java")
    output_files = ["./program/output/" + os.path.basename(file) for file in input_files]
    if len(input_files) != len(output_files):
        logger.error("The number of input files and output files is different.")
        return
    
    target_files =glob.glob("./program/target/*.java")
    
    pair:list[list,list] = []

    for i in range(len(input_files)):
        input_file_path = input_files[i]
        output_file_path = output_files[i]
        logger.info(f"Input file: {input_file_path}")
        logger.info(f"Output file: {output_file_path}")
        input_token_stream: CommonTokenStream = CommonTokenStream(JavaLexer(FileStream(input_file_path, encoding="utf-8")))
        input_token_stream.fill()
        output_token_stream: CommonTokenStream = CommonTokenStream(JavaLexer(FileStream(output_file_path, encoding="utf-8")))
        output_token_stream.fill()
        
        INPUT_LT = 1  # Lookahead 1
        OUTPUT_LT = 1
        
        INPUT_LT_LEN = 0
        OUTPUT_LT_LEN = 0
    
        input_token = input_token_stream.LT(INPUT_LT)  # 最初のトークン（Lookahead 1）
        output_token = output_token_stream.LT(OUTPUT_LT)
        
        # 一つのペアを作る。主に空白や改行が入る
        # 空白や改行ではないのが来たとき、その前後のtokenを見て、同じかどうかを確認する
        # 同じじゃなかったら保存する
        input_pair:list = []
        ouput_pair:list = []
        
        # token:81→ (
        # token:82→ )
        # token:83→ {
        # token:84→ }
        
        # 基本的に、空白と改行以外は同じトークンが来るはず
        while True:  # EOF（End Of File）に達するまでループ
            #print(f"Input: {input_token.type}, {input_token.text}, Output: {output_token.type}, {output_token.text}")
            
            # 見るのは空白(ID:1)とタブ(ID:2)と空行(ID:3)だけ
            if input_token.type == 1 or input_token.type == 2 or input_token.type == 3:
                input_pair.append(input_token.type)
                INPUT_LT_LEN += 1
                input_token = input_token_stream.LT(INPUT_LT + INPUT_LT_LEN)
                continue
            
            if output_token.type == 1 or output_token.type == 2 or output_token.type == 3:
                ouput_pair.append(output_token.type)
                OUTPUT_LT_LEN += 1
                output_token = output_token_stream.LT(OUTPUT_LT + OUTPUT_LT_LEN)
                continue
            
            # 空白改行以外で同じtokenが来たとき
            if input_token.type == output_token.type:
                
                # 空白も改行もこないとき
                if INPUT_LT_LEN != 0 or OUTPUT_LT_LEN != 0:
                    # ペアの頭に前のtypeを追加
                    input_pair.insert(0, input_token_stream.LT(INPUT_LT - 1).type)
                    ouput_pair.insert(0, output_token_stream.LT(OUTPUT_LT - 1).type)
                    
                    # ペアの最後に次のtypeを追加
                    input_pair.append(input_token.type)
                    ouput_pair.append(output_token.type)
                    
                    input_pairs = [p[0] for p in pair]
                
                    # これらのペアが違うなら変換が必要、よってリストに追加
                    # ペアがないことを確認する
                    if (input_pair != ouput_pair) & (input_pair not in input_pairs):
                        
                        pair.append([input_pair, ouput_pair])
                        logger.info(f"make pair: {input_pair} -> {ouput_pair}")
                        
                        # 変換した場合は最後のトークンが最初のトークンになる可能性がある
                        # そのため、トークンを1つ戻す
                        INPUT_LT -= 1
                        OUTPUT_LT -= 1
                
                # 次のトークンへ
                INPUT_LT = INPUT_LT + INPUT_LT_LEN + 1
                OUTPUT_LT = OUTPUT_LT + OUTPUT_LT_LEN + 1
                input_token = input_token_stream.LT(INPUT_LT)
                output_token = output_token_stream.LT(OUTPUT_LT)
                
                # 初期化
                input_pair = []
                ouput_pair = []
                INPUT_LT_LEN = 0
                OUTPUT_LT_LEN = 0
                
                # EOFに達したら終了
                if input_token.type == -1:
                    break
            
            else:
                logger.error(f"raise Error! \nInput: {input_token.text}, Output: {output_token.text}")
                break
            
        
        #ast_info = AstProcessor(logger, BasicInfoListener()).execute(target_file_path)

        #print(pprint.pformat(ast_info, width=80)) # 幅:80文字に整形
    #    pprint.pprint(ast_info)
    
    # 1箇所に2回変換されてインデントが崩れる場合がある
    pair.sort()
    pair.reverse()
    # import pprint
    # pprint.pprint(pair)
    
    # targetファイルを変換していく
    for i in range(len(target_files)):
        target_file_path = target_files[i]
        logger.info(f"Target file: {target_file_path}")
        target_token_stream: CommonTokenStream = CommonTokenStream(JavaLexer(FileStream(target_file_path, encoding="utf-8")))
        target_token_stream.fill()
        
        target_types = []
        target_text = []
        
        TARGET_LT = 1
        
        while True:
            target_token = target_token_stream.LT(TARGET_LT)
            if target_token.type == -1:
                break
            target_types.append(target_token.type)
            target_text.append(target_token.text)
            
            TARGET_LT += 1
        
        # ここで変換
        for p in pair:
            input_pair = p[0]
            output_pair = p[1]
            
            # print("target_length: ", len(target_types))
            for i in range(len(target_types) - len(input_pair) + 1):
                if target_types[i:i+len(input_pair)] == input_pair:
                    exchange_text = [target_text[i]]
                    for j in range(1, len(output_pair)-1):
                        if output_pair[j] == 1:
                            exchange_text.append(" ")
                        elif output_pair[j] == 2:
                            exchange_text.append("\t")
                        elif output_pair[j] == 3:
                            exchange_text.append("\r\n")
                    exchange_text.append(target_text[i+len(input_pair)-1])
                    
                    logger.info(f"Convert: {target_text[i:i+len(input_pair)]} -> {exchange_text}")
                    logger.info(f"Convert: {target_types[i:i+len(input_pair)]} -> {output_pair}")
                    
                    target_text[i:i+len(input_pair)] = exchange_text
                    target_types[i:i+len(input_pair)] = output_pair
        
        # 文字コードがずれるので直す
        target_text = [text.replace("\r\n", "\n") for text in target_text]
        
        # ここで変換後のファイルを作成
        with open(f"./program/exchange/{os.path.basename(target_file_path)}", "w") as f:
            f.write("".join(target_text))


if __name__ == "__main__":
    main()