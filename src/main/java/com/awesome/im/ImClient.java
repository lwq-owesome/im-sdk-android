package com.awesome.im;

import cn.hutool.core.util.IdUtil;
import com.awesome.im.proto.AuthenticateRequestProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author awesome
 *
 */
public class ImClient {
    private   EventLoopGroup eventLoopGroup =null;

    private   Bootstrap bootstrap =null;

    private SocketChannel socketChannel =null;


    public  ChannelFuture connect(String host,int port) throws Exception {

         eventLoopGroup=new NioEventLoopGroup();

             bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {

                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            /*
                             * ProtobufEncoder：用于对Probuf类型序列化。
                             *
                             * ProtobufVarint32LengthFieldPrepender：用于在序列化的字节数组前加上一个简单的包头，只包含序列化的字节长度。
                             *
                             * ProtobufVarint32FrameDecoder：用于decode前解决半包和粘包问题（利用包头中的包含数组长度来识别半包粘包）
                             *
                             * ProtobufDecoder：反序列化指定的Probuf字节数组为protobuf类型。
                             */
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ProtobufVarint32FrameDecoder());
                            pipeline.addLast(new ProtobufDecoder(AuthenticateRequestProto.AuthenticateRequest.getDefaultInstance()));
                            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new ImClientHandler());
                        }
                    });



        ChannelFuture sync = bootstrap.connect(host, port);
           sync.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {

                    if (future.isSuccess()) {
                        socketChannel= (SocketChannel) future.channel();
                        System.out.println("跟TCP接入系统完成长连接的建立");

                    } else {
                        future.channel().close();
                        eventLoopGroup.shutdownGracefully();
                        future.cause().printStackTrace();
                    }

                }
            });


           return sync;


    }


    public  void authenticate(String userId,String token){
        AuthenticateRequestProto.AuthenticateRequest build =
                AuthenticateRequestProto.AuthenticateRequest.newBuilder().setTimestamp(System.currentTimeMillis())
                        .setToken("mdzz").build();
        socketChannel.writeAndFlush(build);
        System.out.println("发起用户认证");

    }

    public void send(String userId, String message) throws Exception {
//        socketChannel.channel().writeAndFlush(s);
//        System.out.println("发送消息成功");

    }

    public void  close ()throws Exception {
        socketChannel.close();
        eventLoopGroup.shutdownGracefully();
        System.out.println("client 端关闭成功");

    }
}
