# 入出力例プログラミングによるJava のコーディングスタイル変換プログラムの生成

以下、バージョンは適切な数字に変えること。

## インストール
1. JavaとPythonの実行環境を準備する。
2. Pythonには```Pyyaml```と```antlr4-Python3-runtime```のパッケージをインストールする。
3. antlr-4.XX.X-complete.jarを[ANTLR公式サイト](http://www.antlr.org)から、JavaLexer.g4、JavaParser.g4を[ANTLRのリポジトリ](https://github.com/antlr/grammars-v4/tree/master/java)からダウンロードする。
4. ダウンロードしたJavaLexer.g4の
```
lexer grammer JavaLexer;
```
の下に
```
WHITESPACE: [ ] ;
TAB: [\t] ;
NEWLINE: '\r\n' ;
```
を追加する。

4. ダウンロードしたファイルと同じディレクトリで以下のコマンドを実行。
```java -jar antlr-4.XX.X-complete.jar -Dlanguage=Python3 *.g4```
5. 同じディレクトリに、このリポジトリからダウンロードした```ast_analyze_executor.py```を上書きする。
6. 同じディレクトリに```inputフォルダ```、```outputフォルダ```、```exchangeフォルダ```、```targetフォルダ```を置く。
7. ```outputフォルダ```にあるjavaソースコードを同じコーディングに沿って編集する。
8. ```targetフォルダ```にコーディングスタイルを変更したいJavaソースコードを置く。
9. ```ast_analyze_executor.py```を実行する。
10. ```exchangeフォルダ```に変換されたJavaソースコードが生成される。
