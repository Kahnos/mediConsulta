package medicoapp;

/**
 * Clase que contiene metodos para validaciones de los imput en los formularios
 * @author Los appeadores
 */
public final class Validations {
    
    // Verifica si un numero es double
    public static boolean isDouble(String str){  
          try {  
            double d = Double.parseDouble(str);  
          }  
          catch(NumberFormatException nfe)  
          {  
            return false;  
          }  
          return true;  
        }
    
    // verifica si un numero es int
    public static boolean isInt(String str){  
          try {  
            int d = Integer.parseInt(str);  
          }  
          catch(NumberFormatException nfe)  
          {  
            return false;  
          }  
          return true;  
        }
}
