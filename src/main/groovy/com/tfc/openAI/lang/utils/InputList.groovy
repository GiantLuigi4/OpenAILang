package com.tfc.openAI.lang.utils

import java.util.function.Consumer

class InputList {
    private static final HashMap<Integer,ArrayList<String>> inputs = new HashMap<>()

    static void add(int id, String name) {
        if (!inputs.containsKey(id)) inputs.put(id, new ArrayList<String>())
        inputs.get(id).add(name)
//        System.out.println(name)
    }

    static void forEach(int id, Consumer<String> stringConsumer) {
        inputs.get(id).forEach(stringConsumer)
        inputs.get(id).clear()
        inputs.remove(id)
    }
}
