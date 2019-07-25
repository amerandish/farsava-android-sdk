package com.amerandish.farsava.ui.util;

/**
 * Created by MHK on 19/07/15.
 * www.MHKSoft.com
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

public class FileChooser {
    private static final String PARENT_DIR = "...";

    private final Context context;
    private ListView list;
    private Dialog dialog;
    private File currentPath;

    // filter on file extension
    private String extension = null;
    private FileSelectedListener fileListener;

    public FileChooser(Context context, String extension) {
        this.context = context;
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
        dialog = new Dialog(context);
        list = new ListView(context);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                String fileChosen = (String) list.getItemAtPosition(which);
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    refresh(chosenFile);
                } else {
                    if (fileListener != null) {
                        fileListener.fileSelected(chosenFile);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(list);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        refresh(Environment.getExternalStorageDirectory());
    }

    public void setExtension(String extension) {
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
    }

    public FileChooser setFileListener(FileSelectedListener fileListener) {
        this.fileListener = fileListener;
        return this;
    }

    public void showDialog() {
        dialog.show();
    }

    /**
     * Sort, filter and display the files for the given path.
     */
    private void refresh(File path) {
        this.currentPath = path;
        if (path.exists() && path.canRead()) {
            // find em all
            TreeSet<String> dirs = new TreeSet<>();
            TreeSet<String> files = new TreeSet<>();
            for (File file : path.listFiles()) {
                if (!file.canRead())
                    continue;
                if (file.isDirectory()) {
                    dirs.add(file.getName());
                } else {
                    if (extension == null || file.getName().toLowerCase().endsWith(extension))
                        files.add(file.getName());
                }
            }

            // convert to an array
            ArrayList<String> fileList = new ArrayList<>(dirs.size() + files.size());
            if (path.getParentFile() != null)
                fileList.add(PARENT_DIR);
            fileList.addAll(dirs);
            fileList.addAll(files);

            // refresh the user interface
            dialog.setTitle(currentPath.getPath());
            list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, fileList) {
                @Override
                public View getView(int pos, View view, ViewGroup parent) {
                    view = super.getView(pos, view, parent);
                    ((TextView) view).setSingleLine(true);
                    return view;
                }
            });
        } else {
            list.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, new String[]{"Can't access " + path}));
        }
    }

    /**
     * Convert a relative filename into an actual File object.
     */
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) {
            return currentPath.getParentFile();
        } else {
            return new File(currentPath, fileChosen);
        }
    }


    // file selection event handling
    public interface FileSelectedListener {
        void fileSelected(File file);
    }
}
