
public class CommanCode {
//    public static Policy policy=new Policy();
//    public static Customer customer=new Customer();
    
    public static void onlyDigitAllowed(java.awt.event.KeyEvent evt){
        //can not enter other then Digits
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }
    public static void onlyAlphabetAllowed(java.awt.event.KeyEvent evt){
        //can not enter other then alphabets and space
        char c=evt.getKeyChar();
        if((!Character.isAlphabetic(c)) && !(c==' ') ){
            evt.consume();
        }
    }
}
