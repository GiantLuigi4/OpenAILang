package com.tfc.openAI.lang

class Files {
    static final String dir = System.getProperty("user.dir")

    static String read(String fileRead) {
        String dirFull = dir + File.separatorChar + fileRead.replace("\\", "" + File.separatorChar).replace("/", "" + File.separatorChar)
        File file = new File(dirFull)
        Scanner sc = new Scanner(file)
        StringBuilder builder = new StringBuilder()
        while (sc.hasNextLine())
            builder.append(sc.nextLine() + "\n")
        sc.close()
        return builder.toString()
    }

    static String readFromClassLoader(String fileRead) {
        InputStream inputStream = ClassLoader.getResourceAsStream(fileRead)
        byte[] bytes = new byte[inputStream.available()]
        inputStream.read(bytes)
        StringBuilder builder = new StringBuilder()
        for (byte b : bytes) {
            builder.append((char) b)
        }
        return builder.toString()
    }
}
