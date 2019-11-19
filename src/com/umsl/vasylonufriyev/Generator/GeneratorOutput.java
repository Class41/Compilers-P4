package com.umsl.vasylonufriyev.Generator;

import com.umsl.vasylonufriyev.Main;

import java.io.*;

class GeneratorOutput {
    private String targetFilename;
    private String targetOutput;

    GeneratorOutput(boolean usingFile) {
        targetFilename = (usingFile) ? Main.inFileName + ".asm" : "kb.asm";
        targetOutput = "";
    }

    public void appendCommand(String command) {
        targetOutput += command;
    }


    public void finalizeAndWrite() {
        File targetFile = new File("./" + targetFilename);

        try {
            if(!targetFile.canWrite())
                throw new IOException();
            BufferedWriter targetOutputStream = new BufferedWriter(new FileWriter(targetFile));
            targetOutputStream.write(targetOutput);
            targetOutputStream.flush();
            targetOutputStream.close();
        } catch (IOException e) {
            System.out.println("Failed to write target file! Aborting: " + targetFile.getAbsolutePath());
            System.exit(-254);
        }
    }
}