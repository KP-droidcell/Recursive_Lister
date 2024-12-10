import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveFileLister extends JFrame {

    private JTextArea textArea;

    public RecursiveFileLister() {
        setTitle("Recursive File Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton button = new JButton("Choose Directory");
        button.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File directory = chooser.getSelectedFile();
                listAllFiles(directory);
            }
        });
        add(button, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void listAllFiles(File directory) {
        textArea.setText("");
        List<File> files = listFilesRecursively(directory);
        for (File file : files) {
            textArea.append(file.getAbsolutePath() + "\n");
        }
    }

    private List<File> listFilesRecursively(File directory) {
        List<File> files = new ArrayList<>();
        if (directory.isDirectory()) {
            File[] entries = directory.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory()) {
                        files.addAll(listFilesRecursively(entry));
                    } else {
                        files.add(entry);
                    }
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecursiveFileLister::new);
    }
}