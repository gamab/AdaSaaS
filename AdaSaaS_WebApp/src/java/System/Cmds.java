/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author gb
 */
public class Cmds {

    public static String cmdListDir() {
        return "ls -1";
    }

    public static String cmdRemoveFilesInDir(List<String> files) {
        String command = "rm ";
        for (String l : files) {
            command += l + " ";
        }
        return command;
    }

    public static String cmdRemoveFilesInDirExceptAdb(List<String> files, String adbFileName) {
        System.out.println("Remove files except : " + adbFileName);
        String command = "rm ";
        for (String l : files) {
            if (!l.equals(adbFileName + ".adb")) {
                command += l + " ";
            }
        }
        return command;
    }

    public static String cmdCompileAdbFile(String fileName) {
        String command = "gnatmake -aI../ada_package/ ";
        command += fileName + ".adb -aO../ada_package/ -o ";
        command += fileName;
        return command;
    }

    public static String cmdCompileAdbFileWEBINF(String fileName, ServletContext ctx) {
        String fullPackagePath = ctx.getRealPath("/WEB-INF/ada_package/");
        String command = "gnatmake -I"+fullPackagePath+"/ ";
        command += fileName + ".adb -o ";
        command += fileName;
        return command;
    }

    public static String cmdExecuteProgram(String program) {
        String command = "./" + program;
        return command;
    }
}
