package com.apptamin.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.apptamin.common.UncatchableException;

/* note temporaire :
 * 
 * par ce socket vont transiter :
 * 
 *  - en upload : 
 *    les valeurs contenues dans ref.txt
 *    (n° de frame, coordonnées x et y du doigt sur l'écran, code pour la position clé suivante, code pour le type d'action)
 *    
 *  - en download :
 *    les valeurs nécessaires pour construire l'overlay de toutes les images
 *    (n° des frames, coordonnées de placement des calques, valeurs de zoom des éléments)
 *  
 */

public class SocketClient {

	public static void main(String args[]) throws IOException,
			InterruptedException {
		int port = 8095;
		Socket s = new Socket();
		String host = "localhost";
		Handler consoleHandler = new Handler(){
	         @Override
	            public void publish(LogRecord record)
	            {
	                if (getFormatter() == null)
	                {
	                    setFormatter(new SimpleFormatter());
	                }

	                try {
	                    String message = getFormatter().format(record);
	                    if (record.getLevel().intValue() >= Level.WARNING.intValue())
	                    {
	                        System.err.write(message.getBytes());                       
	                    }
	                    else
	                    {
	                        System.out.write(message.getBytes());
	                    }
	                } catch (Exception exception) {
	                    reportError(null, exception, ErrorManager.FORMAT_FAILURE);
	                    return;
	                }

	            }

	            @Override
	            public void close() throws SecurityException {}
	            @Override
	            public void flush(){}
	        };
	       
		Logger log = Logger.getLogger("client");
		log.setUseParentHandlers(false);
		log.addHandler(consoleHandler);
		OutputStream outWriter;
		BufferedReader inReader;

		try {
			s.connect(new InetSocketAddress(host, port));
			outWriter = s.getOutputStream();
			inReader = new BufferedReader(new InputStreamReader(
					s.getInputStream()));

			log.info("Socket connected");

			Path path = Paths.get("records/ref.txt");
			byte[] data = Files.readAllBytes(path);
			boolean boucle = true;

			for (int i = 0; i < data.length; i++) {
				outWriter.write(data[i]);
			}
			outWriter.write(-1);
			outWriter.flush();

			boucle = false;  

			int z;
			boolean boucle2 = true;
			int lecture2;
			Scanner scan = null;
			ArrayList<ArrayList<Integer>> ca = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> cl = new ArrayList<Integer>();
			ArrayList<Integer> datas = new ArrayList<Integer>();

			StringBuilder result = new StringBuilder(datas.size());

			while (boucle2) {
				lecture2 = inReader.read();

				if(lecture2 != -1){
					while (true){
						if (lecture2 == 65533) break;
						result.append(Character.toChars(lecture2)[0]);
						lecture2 = inReader.read();
					}

					boucle2 = false;
					String lectureString = result.toString();

					BufferedReader lignes = new BufferedReader(
							new StringReader(lectureString));

					String ligne = lignes.readLine();

					while (ligne != null){
						scan = new Scanner(ligne);
						while (scan.hasNextInt()) {
							z = scan.nextInt();
							cl.add(z);
						}
						ca.add(cl);
						ligne = lignes.readLine();
						cl = new ArrayList<Integer>();
						scan.close();
					}
					// break;

				}

				scan.close();
				boucle = false;

			}
		}

		// Host not found
		catch (UnknownHostException e) {
			throw new UncatchableException("Don't know about host :" + host);
		}
		catch (ConnectException e) {
			throw new UncatchableException("Could not connect to server :" + host);
		}
	}
}
