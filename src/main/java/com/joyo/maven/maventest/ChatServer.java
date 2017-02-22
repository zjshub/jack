package com.joyo.maven.maventest;

import java.io.IOException;

import com.joyo.maven.maventest.hander.HttpHander;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServer {

	public ChatServer() {
		// TODO Auto-generated constructor stub
	}

	private int port = 8080;

	public void start() {
		// 主线程
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 工作线程
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// 所有自定义的业务从这开始
							// 写一段逻辑 对HTTP支持
							// 工作流 流水线
							ChannelPipeline pipeline = ch.pipeline();
							//
							pipeline.addLast(new HttpServerCodec());
							//
							pipeline.addLast(new StringEncoder());
							//
							pipeline.addLast(new HttpObjectAggregator(64 * 1024));
							//用来处理文件流
							pipeline.addLast(new ChunkedWriteHandler());
							//处理http请求的业务逻辑
							pipeline.addLast(new HttpHander());

						}
					});
			ChannelFuture f = b.bind(this.port).sync();
			System.out.println("服务已启动,监听端口" + this.port);
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws IOException {
		new ChatServer().start();
	}
}
