package com.joyo.maven.maventest.hander;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

import org.omg.CORBA.Request;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpHander extends SimpleChannelInboundHandler<FullHttpRequest> {
//	private URL basePaht = HttpHander.class.getProtectionDomain().getCodeSource();
	private final String webroot ="webroot";
	public HttpHander() {
		// TODO Auto-generated constructor stub
	}
	private File getResource(String uri){
//		String resource = basePaht.toURI()+webroot+"/" +uri;
//		System.out.println(path);
		
//		ct
//		path.replace("//", "/");
		
		return null;
		
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		// TODO Auto-generated method stub
	String uri = request.getUri();
	System.out.println("获取URL"+uri);
	String resource = uri.equals("/")? "chat.xml":uri;
	RandomAccessFile file = new RandomAccessFile(getResource(resource), "r");
//	HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus)
	ctx.writeAndFlush("helloworld");
	ctx.close();
	//
	}

}
