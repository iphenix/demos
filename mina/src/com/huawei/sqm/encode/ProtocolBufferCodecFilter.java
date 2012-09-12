package com.huawei.sqm.encode;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;

import com.google.protobuf.ByteString;
import com.huawei.sqm.RequestProtos.Request;

public class ProtocolBufferCodecFilter extends IoFilterAdapter {
    public void messageReceived(NextFilter nextFilter, IoSession session,
            Object message) throws Exception {
    	String msg = (String)message;
    	Request req = Request.parseFrom(ByteString.copyFromUtf8(msg));
    	
        nextFilter.messageReceived(session, req);
    }
    
    public void filterWrite(NextFilter nextFilter, IoSession session,
            WriteRequest writeRequest) throws Exception {
    	Request req = (Request)writeRequest.getMessage();
    	
        nextFilter.filterWrite(session, new DefaultWriteRequest(req.toByteString().toStringUtf8(), writeRequest.getFuture(), writeRequest.getDestination()));
    }
}
