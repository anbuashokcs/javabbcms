package org.javabb.infra;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Dalton Camargo - <a href="mailto:dalton@ag2.com.br">dalton@ag2.com.br</a> <Br>
 * AG2 - Agencia de Inteligencia Digital S.A.<Br>
 * <a href="http://www.ag2.com.br">http://www.ag2.com.br</a><Br>
 * Nosso <i>www</i> e mais <b>inteligente</b>!
 */
public class MaintainProperties {
    private FileInputStream localFile = null;
    private Properties properties = null;
    
    
    public MaintainProperties(FileInputStream file) throws Exception{
        localFile = file;
        loadProperties();
    }
    
    private void loadProperties() throws Exception{
        if(localFile == null){
            throw new Exception("Property is null: FileInputStream !");
        }
        properties = new Properties();
        properties.load(localFile);
    }
    
    public String getProperty(String nmProperty) throws Exception{
        if(properties == null){
            throw new Exception("Property is null !");
        }
        if(nmProperty == null) {
            return null;
        }
        return properties.getProperty(nmProperty);
    }
}
