// **********************************************************************
//
// Copyright (c) 2003-2010 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.4.1

package com.ice.demo.clock;

// <auto-generated>
//
// Generated from file `clock.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>


public final class ClockServiceHelper
{
    public static void
    write(Ice.OutputStream __outS, ClockService __v)
    {
        __outS.writeObject(__v);
    }

    public static void
    read(Ice.InputStream __inS, ClockServiceHolder __h)
    {
        __inS.readObject(__h);
    }
}