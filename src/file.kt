import platform.posix.*

open class file(var fileName: String)
{
	var f = fopen(fileName, "r")

	open inner class wc()
	{
		var line: Int = 0
		var char: Int = 0

		init
		{
			var tmp: Int = fgetc(f).toInt()

			while(tmp != EOF)
			{
				char ++
				if (tmp == '\n'.toInt()) line ++
				tmp = fgetc(f).toInt()
			}
		}

		open fun line(): Int = line
		open fun char(): Int = char
	}

	open inner class output()
	{
		init
		{
			if(fopen(fileName, "r") != NULL)
				f = fopen(fileName, "a")
			else 
				f = fopen(fileName, "w")
		}

		open fun clear()
		{
			f = fopen(fileName, "w") 
		}

		open fun replaceAll(oldChar: String, newChar: String)
		{
			fputs(file(fileName).input().readAll().replace(oldChar, newChar), fopen(fileName, "w"))
		}

		open fun writeToEnd(str: String)
		{
			fputs(str, fopen(fileName, "a"))
		}

		open fun writeToFirst(str: String)
		{
			fputs(str+file(fileName).input().readAll(), fopen(fileName, "w"))
		}

		open fun removeRangeLine(begin: Int = 1,  end: Int)
		{
			var Out: String = ""
			var count: Int = 1

			for (i in file(fileName).input().readAll().split("\n"))
			{
				if (count++ !in begin .. end)
					if (count-1 != file(fileName).wc().line() && end != file(fileName).wc().line()) Out += i+"\n"
					else Out += i
			}

			fputs(Out, fopen(fileName, "w"))
		}

		open fun removeLine(num: Int)
		{
			if (num < file(fileName).wc().line()) file(fileName).output().removeRangeLine(num, num)
			else file(fileName).output().removeRangeLine(num, num+1)
		}
	}

	open inner class input()
	{
		init
		{
			f = fopen(fileName, "r")
		}

		open fun readLine(row: Int = 1): String
		{
			if (file(fileName).wc().line() < row) return "Error"
			var Out: String = ""
			var tmp: Int = fgetc(f)

			for (i in 1 .. row)
			{
				Out = ""
				while (tmp != EOF && tmp != '\n'.toInt())
				{
					Out += tmp.toChar()
					tmp = fgetc(f)
				}

				if (tmp == '\n'.toInt()) tmp = fgetc(f).toInt()
			}

			return Out
		}

		open fun readRangeLine(begin: Int = 1, end: Int): String
		{
			if (begin < 1 || end > file(fileName).wc().line()) return "Error"

			var Out: String = ""

			for (i in begin .. end)
				Out += file(fileName).input().readLine(i)+"\n"

			return Out
		}

		open fun readAll(): String
		{
			var Out: String = ""
			var tmp: Int = fgetc(f)

			while (tmp != EOF)
			{
				Out += tmp.toChar()
				tmp = fgetc(f)
			}

			return Out
		}

		open fun readRow(Sep: String = "\t", row: Int = 1): List<String>
		{
			if (row > file(fileName).wc().line()) return listOf("Error", "Error") 
			return file(fileName).input().readLine(row).split(Sep)
		}

		open fun search(str: String): Boolean
		{
			return (str in file(fileName).input().readAll())
		}

		open fun head(num: Int = 10): String
		{
			if (num > file(fileName).wc().line()) return "Error"
			return file(fileName).input().readRangeLine(1, num)
		}

		open fun tail(num: Int = 10): String
		{
			if (num > file(fileName).wc().line()) return "Error"
			return file(fileName).input().readRangeLine(file(fileName).wc().line()-num, file(fileName).wc().line())
		}

		open fun readColumn(Sep: String = " ", column: Int = 1): List<String>
		{
			var Out: String = ""

			for (i in 1 .. file(fileName).wc().line())
			{
				try
				{
					Out += file(fileName).input().readRow(Sep, i)[column-1]+" "
				}catch (e: Exception)
				{
					Out += ""
				}
			}

			return Out.split(" ")
		}
	}

}

