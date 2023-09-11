package com.cloud.netty;

import com.cloud.netty.handler.TextWebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("http-codec",new HttpServerCodec());//设置解码器
        ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());//用于大数据的分区传输
        ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));//聚合器，使用websocket会用到
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/dsg_cloud"));
        ch.pipeline().addLast("handler",new TextWebSocketFrameHandler());//自定义的业务handler
	}
}
