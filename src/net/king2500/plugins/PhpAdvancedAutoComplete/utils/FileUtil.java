package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 16.08.13
 * Time: 19:09
 */
public class FileUtil {
    static final public int TYPE_ALL = 0;
    static final public int TYPE_FILE = 1;
    static final public int TYPE_DIR = 2;

    public static String[] getProjectFiles(Project project)
    {
        final List<String> files = new ArrayList<String>();
        final VirtualFile projectDirectory = project.getBaseDir();

        ProjectFileIndex fileIndex = ProjectFileIndex.SERVICE.getInstance(project);

        fileIndex.iterateContentUnderDirectory(projectDirectory, new ContentIterator() {
            @Override
            public boolean processFile(VirtualFile file) {
                String relativePath = file.getPath().replace(projectDirectory.getPath() + "/", "");

                //files.add(file.getName());
                if(!file.isDirectory() && !relativePath.startsWith(".idea/")) {
                    files.add(relativePath);
                }

                return true;
            }
        }
        );

        return files.toArray(new String[files.size()]);
    }

    public static String[] getRelativeFiles(PsiFile baseFile)
    {
        final List<String> files = new ArrayList<String>();
        Project project = baseFile.getProject();
        final VirtualFile projectDirectory = project.getBaseDir();
        PsiDirectory directory = baseFile.getContainingDirectory();

        if(directory == null)
            return null;

        final VirtualFile originDirectory = directory.getVirtualFile();

        ProjectFileIndex fileIndex = ProjectFileIndex.SERVICE.getInstance(project);

/*
        for(VirtualFile file : originDirectory.getChildren()) {
            files.add(file.getPath().replace(projectDirectory.getPath() + "/", ""));
        }
*/

        fileIndex.iterateContentUnderDirectory(originDirectory, new ContentIterator() {
                @Override
                public boolean processFile(VirtualFile file) {
                    //files.add(file.getName());
                    if(!file.isDirectory()) {
                        files.add(file.getPath().replace(originDirectory.getPath() + "/", ""));
                    }

                    return true;
                }
            }
        );

        return files.toArray(new String[files.size()]);
    }

    public static Map<String, PsiFileSystemItem> getRelativeFilesByName(PsiFile baseFile, final int fileType) {
        final Map<String, PsiFileSystemItem> files = new HashMap<String, PsiFileSystemItem>();
        final Project project = baseFile.getProject();

        final VirtualFile projectDirectory = project.getBaseDir();

        PsiDirectory directory = baseFile.getContainingDirectory();

        if(directory == null)
            directory = baseFile.getOriginalFile().getContainingDirectory();

        if(directory == null)
            return files;

        final VirtualFile originDirectory = directory.getVirtualFile();

        ProjectFileIndex fileIndex = ProjectFileIndex.SERVICE.getInstance(project);

        if(projectDirectory != null) {

            if(fileType == TYPE_DIR) {
                PsiDirectory psiDir = PsiManager.getInstance(project).findDirectory(originDirectory);

                if (psiDir != null) {
                    files.put(".", psiDir);
                }
            }

            fileIndex.iterateContentUnderDirectory(originDirectory, new ContentIterator() {
                @Override
                public boolean processFile(VirtualFile virtualFile) {
                    String relativePath = virtualFile.getPath().replace(originDirectory.getPath() + "/", "");

                    if((fileType == TYPE_ALL || fileType == TYPE_FILE) && !virtualFile.isDirectory() && !relativePath.startsWith(".idea/")) {
                        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);

                        if (psiFile != null) {
                            files.put(relativePath, psiFile);
                        }
                    }

                    if((fileType == TYPE_ALL || fileType == TYPE_DIR) && virtualFile.isDirectory() && !relativePath.startsWith(".idea") && !virtualFile.getPath().equals(originDirectory.getPath())) {
                        PsiDirectory psiDir = PsiManager.getInstance(project).findDirectory(virtualFile);

                        if (psiDir != null) {
                            files.put(relativePath, psiDir);
                        }
                    }

                    return true;
                }
            }
            );
        }

        return files;
    }
}
