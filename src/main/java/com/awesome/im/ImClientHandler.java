package com.awesome.im;

import cn.hutool.core.util.IdUtil;
import com.awesome.im.proto.AuthenticateRequestProto;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

/**
 * @author awesome
 */
public class ImClientHandler  extends SimpleChannelInboundHandler<AuthenticateRequestProto.AuthenticateRequest> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx)  {


    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

//        AuthenticateRequestProto.AuthenticateRequest build = AuthenticateRequestProto.AuthenticateRequest.newBuilder().setTimestamp(System.currentTimeMillis())
//                .setToken(IdUtil.fastUUID()).build();
//        ctx.writeAndFlush(build);
//
//        System.out.println("channelActive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AuthenticateRequestProto.AuthenticateRequest msg) throws Exception {

    }
}
