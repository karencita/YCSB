/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yahoo.ycsb.kvcliente;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.Status;
import com.yahoo.ycsb.StringByteIterator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Evelyn Gonzalez and Karen Ponce

 */
public class Cliente extends DB {
 private Cliente cl;

    public static final String HOST_PROPERTY = "KeyValueStore.host";
    public static final String PORT_PROPERTY = "KeyValueStore.port";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String host = "";
        String valida = null;
        int port = 0;
        
        if(args.length>0 && args[0] != null && !args[0].equals("")){
            host = args[0];
        } else {
            System.out.println("ERROR: Debe enviar el host");
            System.exit(0);
        }
        if(args.length>1 && args[1] !=null){
            try{
                port = Integer.parseInt(args[1]);
            }catch(Exception e){
                System.out.println("ERROR: Puerto invalido");
                System.exit(0);
            }
        } else {
            System.out.println("ERROR: Debe enviar el puerto");
            System.exit(0);
        }
        
        
        System.out.println("Bienvenidos KEY/VALUE Store");
        System.out.println("Ingrese el comando ó 'help' para ayuda");
        try {
            kkSocket = new Socket(host, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("No tiene acceso al host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Se ha perdido la conexion..");
            System.exit(1);
        }

        // new KVValidacionesCliente();
        KVValidacionesCliente KVValidacionesClient = new KVValidacionesCliente();
        Scanner scannerFile = new Scanner(System.in);
        while (scannerFile.hasNextLine() && (valida = KVValidacionesClient.validate(scannerFile.nextLine())) != null) {
            //if (valida != null) {
            if (!valida.equals("error")) {
                String fromServer;
                //String fromUser;
                out.println(valida);
                try {
                    while ((fromServer = in.readLine()) != null) {
                        System.out.println("Server: " + fromServer);
                        System.out.println("-----------------------------------------------");
                        break;
                    }
                } catch (SocketException se) {
                    System.out.println("ERROR: Problemas de conexion con el servidor");
                }
            }
            System.out.println("-----------------------------------------------");
        }

        out.close();
        in.close();
        kkSocket.close();
    }
/* public void init() throws DBException {
    try {
            Properties props = getProperties();
            String port;

            String portString = props.getProperty(PORT_PROPERTY);
            if (portString != null) {
                port = portString;
            } else {
                port = "1000";
            }
            String host = props.getProperty(HOST_PROPERTY);
            if (host != null) {
                host = "localhost";
            }
            cl = new KVValidacionesCliente(host, port);
            cl.startClient();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
  }*/
    @Override
    public Status read(String table, String key, Set<String> fields, HashMap<String, ByteIterator> result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Status scan(String table, String startkey, int recordcount, Set<String> fields, Vector<HashMap<String, ByteIterator>> result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Status update(String table, String key, HashMap<String, ByteIterator> values) {
        if(insert(table,key, values).equals(Status.OK)){
            return Status.OK;
        }else
            return Status.ERROR;
    }

   /*@Override*/
   /* public Status insert(String table, String key, HashMap<String, ByteIterator> values) {
          try {
            String comando="[put, " + key + ", " + StringByteIterator.getStringMap(values) + "]";
            cl.out.println(comando);
            String respuesta;
            while (((respuesta = cl.in.readLine()) != null) && respuesta.equals("OK")) {
               // System.out.println(comando);
                //System.out.println(respuesta);

                return Status.OK;
            }
            return Status.ERROR;
        } catch (IOException ex) {

            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            return Status.ERROR;
        }
    }
*/
    @Override
    public Status delete(String table, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Status insert(String table, String key, HashMap<String, ByteIterator> values) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
