import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test3 {
    public static void main(String[] args) throws Exception{
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String exp = "'VAY_TRUNG_DAI_HAN'=='VAY_NGAN_HAN'||'VAY_TRUNG_DAI_HAN'=='VAY_TRUNG_DAI_HAN'&&is_private_business_owner!='null'&&1==1&&120>60&&'NORMALLY'=='NORMALLY'&&'ACTUALLY_RECEIVED'=='ACTUALLY_RECEIVED'";
        System.out.println(engine.eval(exp));
    }
}