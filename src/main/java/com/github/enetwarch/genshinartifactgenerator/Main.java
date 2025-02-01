package com.github.enetwarch.genshinartifactgenerator;
import com.github.enetwarch.genshinartifactgenerator.artifact.Generator;
import com.github.enetwarch.genshinartifactgenerator.artifact.Artifact;
import com.github.enetwarch.genshinartifactgenerator.util.Output;
import com.github.enetwarch.genshinartifactgenerator.util.Input;

public class Main {

    public static void main(String[] args) {
        while (true) {
            Artifact artifact = new Artifact(new Generator());
            artifact.logToConsole();
            boolean proceedOrNot = Input.getUserInputBoolean("Make another artifact");
            Output.printSpace();
            if (!proceedOrNot) {
                Output.terminateProgram();
            }
        }
    }

}