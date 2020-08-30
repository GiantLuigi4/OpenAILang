package com.tfc.openAI.lang.utils

class StringDuplicator {
    static String dupe(String text, int count) {
        StringBuilder out = new StringBuilder()
        for (int i=0;i<count;i++) {
            out.append(text)
        }
        return out.toString()
    }
}
