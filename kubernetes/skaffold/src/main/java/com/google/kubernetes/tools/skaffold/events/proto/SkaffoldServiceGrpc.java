/*
 * Copyright 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.kubernetes.tools.skaffold.events.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.19.0)",
    comments = "Source: skaffold.proto")
public final class SkaffoldServiceGrpc {

  private SkaffoldServiceGrpc() {}

  public static final String SERVICE_NAME = "proto.SkaffoldService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      Skaffold.State> getGetStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetState",
      requestType = com.google.protobuf.Empty.class,
      responseType = Skaffold.State.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      Skaffold.State> getGetStateMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, Skaffold.State> getGetStateMethod;
    if ((getGetStateMethod = SkaffoldServiceGrpc.getGetStateMethod) == null) {
      synchronized (SkaffoldServiceGrpc.class) {
        if ((getGetStateMethod = SkaffoldServiceGrpc.getGetStateMethod) == null) {
          SkaffoldServiceGrpc.getGetStateMethod = getGetStateMethod = 
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, Skaffold.State>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.SkaffoldService", "GetState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Skaffold.State.getDefaultInstance()))
                  .setSchemaDescriptor(new SkaffoldServiceMethodDescriptorSupplier("GetState"))
                  .build();
          }
        }
     }
     return getGetStateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Skaffold.LogEntry,
      Skaffold.LogEntry> getEventLogMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EventLog",
      requestType = Skaffold.LogEntry.class,
      responseType = Skaffold.LogEntry.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Skaffold.LogEntry,
      Skaffold.LogEntry> getEventLogMethod() {
    io.grpc.MethodDescriptor<Skaffold.LogEntry, Skaffold.LogEntry> getEventLogMethod;
    if ((getEventLogMethod = SkaffoldServiceGrpc.getEventLogMethod) == null) {
      synchronized (SkaffoldServiceGrpc.class) {
        if ((getEventLogMethod = SkaffoldServiceGrpc.getEventLogMethod) == null) {
          SkaffoldServiceGrpc.getEventLogMethod = getEventLogMethod = 
              io.grpc.MethodDescriptor.<Skaffold.LogEntry, Skaffold.LogEntry>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "proto.SkaffoldService", "EventLog"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Skaffold.LogEntry.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Skaffold.LogEntry.getDefaultInstance()))
                  .setSchemaDescriptor(new SkaffoldServiceMethodDescriptorSupplier("EventLog"))
                  .build();
          }
        }
     }
     return getEventLogMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Skaffold.Event,
      com.google.protobuf.Empty> getHandleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Handle",
      requestType = Skaffold.Event.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Skaffold.Event,
      com.google.protobuf.Empty> getHandleMethod() {
    io.grpc.MethodDescriptor<Skaffold.Event, com.google.protobuf.Empty> getHandleMethod;
    if ((getHandleMethod = SkaffoldServiceGrpc.getHandleMethod) == null) {
      synchronized (SkaffoldServiceGrpc.class) {
        if ((getHandleMethod = SkaffoldServiceGrpc.getHandleMethod) == null) {
          SkaffoldServiceGrpc.getHandleMethod = getHandleMethod = 
              io.grpc.MethodDescriptor.<Skaffold.Event, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.SkaffoldService", "Handle"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Skaffold.Event.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new SkaffoldServiceMethodDescriptorSupplier("Handle"))
                  .build();
          }
        }
     }
     return getHandleMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SkaffoldServiceStub newStub(io.grpc.Channel channel) {
    return new SkaffoldServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SkaffoldServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SkaffoldServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SkaffoldServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SkaffoldServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SkaffoldServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getState(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<Skaffold.State> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStateMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Skaffold.LogEntry> eventLog(
        io.grpc.stub.StreamObserver<Skaffold.LogEntry> responseObserver) {
      return asyncUnimplementedStreamingCall(getEventLogMethod(), responseObserver);
    }

    /**
     */
    public void handle(Skaffold.Event request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getHandleMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                Skaffold.State>(
                  this, METHODID_GET_STATE)))
          .addMethod(
            getEventLogMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Skaffold.LogEntry,
                Skaffold.LogEntry>(
                  this, METHODID_EVENT_LOG)))
          .addMethod(
            getHandleMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Skaffold.Event,
                com.google.protobuf.Empty>(
                  this, METHODID_HANDLE)))
          .build();
    }
  }

  /**
   */
  public static final class SkaffoldServiceStub extends io.grpc.stub.AbstractStub<SkaffoldServiceStub> {
    private SkaffoldServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkaffoldServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkaffoldServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkaffoldServiceStub(channel, callOptions);
    }

    /**
     */
    public void getState(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<Skaffold.State> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Skaffold.LogEntry> eventLog(
        io.grpc.stub.StreamObserver<Skaffold.LogEntry> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getEventLogMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void handle(Skaffold.Event request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getHandleMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SkaffoldServiceBlockingStub extends io.grpc.stub.AbstractStub<SkaffoldServiceBlockingStub> {
    private SkaffoldServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkaffoldServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkaffoldServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkaffoldServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Skaffold.State getState(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetStateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty handle(Skaffold.Event request) {
      return blockingUnaryCall(
          getChannel(), getHandleMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SkaffoldServiceFutureStub extends io.grpc.stub.AbstractStub<SkaffoldServiceFutureStub> {
    private SkaffoldServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkaffoldServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkaffoldServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkaffoldServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Skaffold.State> getState(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> handle(
        Skaffold.Event request) {
      return futureUnaryCall(
          getChannel().newCall(getHandleMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STATE = 0;
  private static final int METHODID_HANDLE = 1;
  private static final int METHODID_EVENT_LOG = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SkaffoldServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SkaffoldServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STATE:
          serviceImpl.getState((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<Skaffold.State>) responseObserver);
          break;
        case METHODID_HANDLE:
          serviceImpl.handle((Skaffold.Event) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EVENT_LOG:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.eventLog(
              (io.grpc.stub.StreamObserver<Skaffold.LogEntry>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SkaffoldServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SkaffoldServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Skaffold.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SkaffoldService");
    }
  }

  private static final class SkaffoldServiceFileDescriptorSupplier
      extends SkaffoldServiceBaseDescriptorSupplier {
    SkaffoldServiceFileDescriptorSupplier() {}
  }

  private static final class SkaffoldServiceMethodDescriptorSupplier
      extends SkaffoldServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SkaffoldServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SkaffoldServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SkaffoldServiceFileDescriptorSupplier())
              .addMethod(getGetStateMethod())
              .addMethod(getEventLogMethod())
              .addMethod(getHandleMethod())
              .build();
        }
      }
    }
    return result;
  }
}
