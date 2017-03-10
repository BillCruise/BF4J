# BF4J

A simple [Brainfuck](https://en.wikipedia.org/wiki/Brainfuck) interpreter in Java.

The language consists of only eight symbols and a data pointer.

* **>** increment the data pointer (to point to the next cell to the right).
* **<** decrement the data pointer (to point to the next cell to the left).
* **+**	 increment (increase by one) the byte at the data pointer.
* **-** decrement (decrease by one) the byte at the data pointer.
* **.** output the byte at the data pointer.
* **,** accept one byte of input, storing its value in the byte at the data pointer.
* **[** if the byte at the data pointer is zero, then instead of moving the instruction pointer forward to the next command, jump it forward to the command after the matching ] command.
* **]** if the byte at the data pointer is nonzero, then instead of moving the instruction pointer forward to the next command, jump it back to the command after the matching [ command.

The "virtual machine" is just an array of bytes initialized to 0. The data pointer initially points to the first byte in this memory pool.
