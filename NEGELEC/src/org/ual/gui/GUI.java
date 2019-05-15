package org.ual.gui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import org.ual.negelec.*;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * FileChooserDemo.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class GUI extends JPanel implements ActionListener {
	static private final String newline = "\n";
	JButton openButton, saveButton, generate, execute;
	JTextArea log;
	JFileChooser fc;
	JTextField minimumSupport;
	JTextField numeroInstancias;
	String saveFile, loadFile;
	JComboBox<String> comboComponentes;
	public StringBuilder salida = new StringBuilder();

	public GUI() {
		super(new BorderLayout());

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create a file chooser
		try {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("DATA FILES *.dat", "dat");
			fc = new JFileChooser(new java.io.File(".").getCanonicalPath() + "\\dataset");
			fc.setFileFilter(filter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Uncomment one of the following lines to try a different
		// file selection mode. The first allows just directories
		// to be selected (and, at least in the Java look and feel,
		// shown). The second allows both files and directories
		// to be selected. If you leave these lines commented out,
		// then the default mode (FILES_ONLY) will be used.
		//
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create the open button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		openButton = new JButton("Open a File...", createImageIcon("../images/Open16.gif"));
		openButton.addActionListener(this);

		// Create the save button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).

		saveButton = new JButton("Save a File...", createImageIcon("../images/Save16.gif"));
		saveButton.addActionListener(this);

		generate = new JButton("Generar archivo de datos");
		generate.addActionListener(this);

		execute = new JButton("Ejecutar algoritmo apriori");
		execute.addActionListener(this);

		minimumSupport = new JTextField(2);
		minimumSupport.setText("0");
		
		JLabel minimumSupportLabel = new JLabel("minimumSupport");

		numeroInstancias = new JTextField(4);
		numeroInstancias.setText("0");
		JLabel numeroInstanciasLabel = new JLabel("numeroInstancias");

		comboComponentes = new JComboBox<>();
		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(openButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(generate);

		JPanel inputPanel = new JPanel();
		inputPanel.add(minimumSupport);
		inputPanel.add(minimumSupportLabel);
		inputPanel.add(numeroInstancias);
		inputPanel.add(numeroInstanciasLabel);
		inputPanel.add(comboComponentes);
		inputPanel.add(execute);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(inputPanel, BorderLayout.AFTER_LAST_LINE);
		add(logScrollPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(GUI.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				log.append("Opening: " + file.getAbsolutePath() + "." + newline);
				saveFile = file.getAbsolutePath();
			} else {
				log.append("Open command cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());

			// Handle save button action.
		} else if (e.getSource() == saveButton) {
			int returnVal = fc.showSaveDialog(GUI.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would save the file.
				log.append("Saving: " + file.getAbsolutePath() + "." + newline);
				saveFile = file.getAbsolutePath();
			} else {
				log.append("Save command cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		}

		// Handle generate button
		else if (e.getSource() == generate) {

			if (saveFile == null) {
				System.err.println("Debe seleccionar un archivo para guardar");
				log.append("Debe seleccionar un archivo para guardar" + newline);
			}

			if ((Integer.parseInt(minimumSupport.getText()) < 1)) {
				System.err.println("El soporte debe ser mayor o igual que 1");
				log.append("El soporte debe ser mayor o igual que 1" + newline);
			}

			if ((Integer.parseInt(numeroInstancias.getText()) < 10)) {

				System.err.println("El numero de instancias no puede estar vacio y debe ser mayor o igual que 10");
				log.append("El numero de instancias no puede estar vacio y debe ser mayor o igual que 10" + newline);

			} else {
				Generador generador = new Generador(Integer.parseInt(minimumSupport.getText()),
						Integer.parseInt(numeroInstancias.getText()), saveFile);
				log.append(generador.salida.toString());
				for (int i = 0; i < generador.conjuntoCompleto.length; i++) {

					comboComponentes.addItem(generador.conjuntoCompleto[i]);
				}

			}
		}
		// Handle execute button action.
		else if (e.getSource() == execute) {
			try {
				Apriori apriori = new Apriori(saveFile, comboComponentes.getSelectedItem().toString() );
				log.selectAll();
				log.replaceSelection("");
				log.append(apriori.salida.toString()+ newline);
//				log.append("--------------------------------------------------------"+ newline);
//				apriori.pw.println("--------------------------------------------------------"+ newline);
//				salida.append("--------------------------------------------------------"+ newline);
//				for (List<String> lista : apriori.lista) {
//					if (lista.contains(comboComponentes.getSelectedItem().toString()) && lista.size()>2) {
//						log.append(lista.toString()+ newline);
//						salida.append(lista.toString()+ newline);
//						apriori.pw.println(lista.toString()+ newline);
//						
//					}
//				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GUI.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Negocio Electronico");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new GUI());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}
