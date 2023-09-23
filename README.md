# crypto
Программа позволяет зашифровывать и расшифровывать текстовый файл.
Зашифровка происходит методом Цезаря. (подстановка символов циклическим сдвигом)
Расшифровать можно только текст, зашифрованный методом Цезаря. 
Расшифровку можно произвести тремя способами:

1. используя ключ.
2. методом подбора ключа.
3. статистическим методом.

При расшифровке по ключу Вы должны знать ключ.

При расшифровке методом подбора программа проверяет все возможные ключи.
Количество возможных комбинаций равно мощности используемого алфавита. 
Имеется в виду, что алфавит - это перечень всех символов, которые участвуют в шифровании.
На каждой итерации происходит проверка правильности расшифровки. 
Критерием верной расшифровки является проверка принципа, что после знаков препинания
обычно следует пробел. 

При расшифровке статистическим методом используется сравнение частоты использования
символов в зашифрованном тексте и незашифрованном тексте. В каждом тексте вычисляется по 
несколько символов, которые чаще всего попадаются в тексте. Затем по положению этих символов
в алфавите вычисляется ключ. 

