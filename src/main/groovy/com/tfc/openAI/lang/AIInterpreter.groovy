package com.tfc.openAI.lang


import com.tfc.openAI.lang.utils.Color
import com.tfc.openAI.lang.utils.InputList
import org.python.util.PythonInterpreter

import java.util.function.Consumer

class AIInterpreter {
    final PythonInterpreter interpreter = createInterp()

    private final PythonInterpreter createInterp() {
        System.setProperty("python.import.site", "false")
        return new PythonInterpreter()
    }

    String interpret(String code) {
        StringBuilder builder = new StringBuilder()
        builder.append("from com.tfc.openAI.lang.utils import InputList\n" +
                "\n" +
                "\n" +
                "getPixel = %display%\n" +
                "aiInstance = %id%\n" +
                "\n" +
                "\n" +
                "def keyPress(key):\n" +
                " InputList.add(aiInstance, 'key:'+str(key))\n" +
                "\n" +
                "\n" +
                "def click(key):\n" +
                " InputList.add(aiInstance, 'click:'+str(key))\n" +
                "\n" +
                "\n" +
                "def mouseMove(key):\n" +
                " InputList.add(aiInstance, 'mouseMove:'+str(key))\n" +
                "\n" +
                "\n" +
                "def AI():\n"
        )
        for (String s : code.split("\n")) {
            String indentedLine = s
            int indents = 0
            String line = ""
            boolean hitLetter = false
            for (int i = 0; i < indentedLine.length(); i++) {
                if (!hitLetter) {
                    if (indentedLine.charAt(i).toString() == ' ') {
                        indents++
                    } else {
                        hitLetter = true
                        line += indentedLine.charAt(i)
                    }
                } else {
                    line += indentedLine.charAt(i)
                }
            }
            indents /= '    '.length()
            indents += 1
            line = line.replace("if:", "if ")
            StringBuilder lineBuilder = new StringBuilder()
            StringBuilder word = new StringBuilder()
            boolean isArray = false
            boolean isColor = false
            boolean isIf = line.startsWith('if')
            int r = -1, g = -1, b = -1
            boolean isArgs = false
            for (char c : line.toCharArray()) {
                if (c.isLetter()) {
                    word.append(c)
                } else if (c == (char) ':') {
                    if (word.toString() == "getPixel") {
                        lineBuilder.append(word.append("[").toString())
                        word = new StringBuilder()
                        isArray = true
                    } else if (word.toString() == "rgb") {
                        word = new StringBuilder()
                        isColor = true
                    } else if (word.toString() == 'else') {
                        word.append(c)
                    } else {
                        word.append("('")
                        isArgs = true
                    }
                } else if ((c.isDigit() && c.toString() != '=') || c.toString() == ',') {
                    if (isArray && c.toString() == ',') {
                        word.append('][')
                    } else if (isColor && c.toString() == ',') {
                        if (r == -1) r = Integer.parseInt(word.toString())
                        else if (g == -1) g = Integer.parseInt(word.toString())
                        else if (b == -1) b = Integer.parseInt(word.toString())
                        word = new StringBuilder()
                    } else {
                        if (c.toString() == '=') {
                            word.append(' ')
                            word.append(c)
                            word.append(c)
                            word.append(' ')
                        } else {
                            word.append(c)
                        }
                    }
                } else {
                    if (isArray) {
                        word.append("]")
                        isArray = false
                        if (c.toString() == '=') {
                            word.append(' ')
                            word.append(c)
                            word.append(c)
                            word.append(' ')
                        } else {
                            word.append(c)
                        }
                    } else if (isColor) {
                        if (r == -1) r = Integer.parseInt(word.toString())
                        else if (g == -1) g = Integer.parseInt(word.toString())
                        else if (b == -1) b = Integer.parseInt(word.toString())
                        word = new StringBuilder()
                        word.append(Color.toInt(r, g, b, 0))
                        r = -1
                        g = -1
                        b = -1
                    } else if (isArgs) {
                        word.append("')")
                        isArgs = false
                    } else if (c.toString() == '=') {
                        word.append(' ')
                        word.append(c)
                        word.append(c)
                        word.append(' ')
                    } else {
                        word.append(c)
                    }
                    lineBuilder.append(word.toString())
                    word = new StringBuilder()
                }
            }
            if (isArgs) {
                word.append("')")
            }
            lineBuilder.append(word.toString())
            if (isIf) {
                builder.append(com.tfc.openAI.lang.utils.StringDuplicator.dupe(' ', indents) + (lineBuilder.append(":\n").toString()))
            } else {
                builder.append(com.tfc.openAI.lang.utils.StringDuplicator.dupe(' ', indents) + (lineBuilder.append("\n").toString()))
            }
        }
        builder.append("" +
                "\n" +
                "\n" +
                "AI()\n")
        return builder.toString()
    }

    String interpretFromCL(String name) {
        String text = Files.readFromClassLoader(name)
        return interpret(text)
    }

    String interpretFromFile(String name) {
        String text = Files.read(name)
        return interpret(text)
    }

    void exec(String compiled, Consumer<String> outputConsumer, int aiInstance, int[][] display) {
        interpreter.eval(compiled.replace("%display%", Arrays.deepToString(display)).replace("%id%", "" + aiInstance))
        InputList.forEach(aiInstance, outputConsumer)
        interpreter.cleanup()
    }

    void close() {
        interpreter.close()
        interpreter.finalize()
    }
}