package com.juliano.carrental.gui.listeners;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.juliano.carrental.gui.CreateCarFrame;

public class LoadImageBtnActionListener implements ActionListener {
    private CreateCarFrame frame;
    private JLabel imgLabel;

    public LoadImageBtnActionListener(CreateCarFrame frame, JLabel imgLabel) {
        this.frame = frame;
        this.imgLabel = imgLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imagens JPG e PNG", "jpg", "jpeg", "png");
        fc.setFileFilter(filter);
        if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                this.frame.setImagePath(fc.getSelectedFile().getAbsolutePath());
                ImageIcon imgIcon = new ImageIcon(fc.getSelectedFile().toURI().toURL());

                Image image = imgIcon.getImage(); // transform it
                Image newimg = image.getScaledInstance(150, 70, java.awt.Image.SCALE_SMOOTH);
                imgIcon = new ImageIcon(newimg); // transform it back
                this.imgLabel.setIcon(imgIcon);

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

    }

}
