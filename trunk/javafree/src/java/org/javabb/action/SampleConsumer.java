package org.javabb.action;

import org.javabb.action.infra.BaseAction;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;

public class SampleConsumer extends BaseAction {

    private static final long serialVersionUID = 1L;

    public ConsumerManager manager;

    public SampleConsumer() throws ConsumerException {
	manager = new ConsumerManager();
    }
/*
    public String validaLogin() throws Exception {
	String _returnURL = "http://example.com/openid";
	
	// perform discovery on the user-supplied identifier
	List discoveries = manager.discover(userSuppliedString);

	// attempt to associate with the OpenID provider
	// and retrieve one service endpoint for authentication
	DiscoveryInformation discovered = manager.associate(discoveries);

	// store the discovery information in the user's session for later use
	// leave out for stateless operation / if there is no session
	setSessionAttribute("discovered", discovered);

	// obtain a AuthRequest message to be sent to the OpenID provider
	AuthRequest authReq = manager.authenticate(discovered, _returnURL);

	return "";

    }*/

}
