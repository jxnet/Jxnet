/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#include <jni.h>

#include <fcntl.h>
#include "utils.h"

jclass StringBuilderClass = NULL;
jmethodID StringBuilderSetLengthMID = NULL;
jmethodID StringBuilderAppendMID = NULL;

void SetStringBuilderIDs(JNIEnv *env) {

	StringBuilderClass = (*env)->FindClass(env, "java/lang/StringBuilder");

	if(StringBuilderClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class java.lang.StringBuilder");
		return;
	}

	StringBuilderSetLengthMID = (*env)->GetMethodID(env, StringBuilderClass, "setLength", "(I)V");

	if(StringBuilderSetLengthMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method StringBuilder.setLength(int)");
		return;
	}

	StringBuilderAppendMID = (*env)->GetMethodID(env, StringBuilderClass, "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");

	if(StringBuilderAppendMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method StringBuilder.append(String)");
		return;
	}
}

jclass ListClass = NULL;
jmethodID ListAddMID = NULL;

void SetListIDs(JNIEnv *env) {

	ListClass = (*env)->FindClass(env, "java/util/List");

	if(ListClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class java.util.List");
		return;
	}

	ListAddMID = (*env)->GetMethodID(env, ListClass, "add", "(Ljava/lang/Object;)Z");

	if(ListAddMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method List.add(Object)");
		return;
	}
}

jclass PcapIfClass = NULL;
jfieldID PcapIfNextFID = NULL;
jfieldID PcapIfNameFID = NULL;
jfieldID PcapIfDescriptionFID = NULL;
jfieldID PcapIfAddressesFID = NULL;
jfieldID PcapIfFlagsFID = NULL;

void SetPcapIfIDs(JNIEnv *env) {

	PcapIfClass = (*env)->FindClass(env, "com/ardikars/jxnet/PcapIf");

	if(PcapIfClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.PcapIf");
		return;
	}

	PcapIfNextFID = (*env)->GetFieldID(env, PcapIfClass, "next", "Lcom/ardikars/jxnet/PcapIf;");

	if(PcapIfNextFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapIf.next:PcapIf");
		return;
	}

	PcapIfNameFID = (*env)->GetFieldID(env, PcapIfClass, "name", "Ljava/lang/String;");

  	if(PcapIfNameFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapIf.name:String");
		return;
	}

	PcapIfDescriptionFID = (*env)->GetFieldID(env, PcapIfClass, "description", "Ljava/lang/String;");

  	if(PcapIfDescriptionFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapIf.description:String");
		return;
	}

	PcapIfAddressesFID = (*env)->GetFieldID(env, PcapIfClass, "addresses", "Ljava/util/List;");

	if(PcapIfAddressesFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field JxpcapIf.addresses:List");
  		return;
	}

	PcapIfFlagsFID = (*env)->GetFieldID(env, PcapIfClass, "flags", "I");

	if(PcapIfNameFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapIf.flags:int");
		return;
	}
}

jclass PcapAddrClass = NULL;
jfieldID PcapAddrNextFID = NULL;
jfieldID PcapAddrAddrFID = NULL;
jfieldID PcapAddrNetmaskFID = NULL;
jfieldID PcapAddrBroadAddrFID = NULL;
jfieldID PcapAddrDstAddrFID = NULL;

void SetPcapAddrIDs(JNIEnv *env) {

	PcapAddrClass = (*env)->FindClass(env, "com/ardikars/jxnet/PcapAddr");

	if(PcapAddrClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.PcapAddr");
		return;
	}

	PcapAddrNextFID = (*env)->GetFieldID(env, PcapAddrClass, "next", "Lcom/ardikars/jxnet/PcapAddr;");

	if(PcapAddrNextFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapAddr.next:PcapAddr");
		return;
	}

	PcapAddrAddrFID = (*env)->GetFieldID(env, PcapAddrClass, "addr", "Lcom/ardikars/jxnet/SockAddr;");

	if(PcapAddrAddrFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapAddr.addr:SockAddr");
		return;
	}

	PcapAddrNetmaskFID = (*env)->GetFieldID(env, PcapAddrClass, "netmask", "Lcom/ardikars/jxnet/SockAddr;");

	if(PcapAddrNetmaskFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapAddr.netmask:SockAddr");
		return;
	}

	PcapAddrBroadAddrFID = (*env)->GetFieldID(env, PcapAddrClass, "broadaddr", "Lcom/ardikars/jxnet/SockAddr;");

	if(PcapAddrBroadAddrFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapAddr.broadaddr:SockAddr");
		return;
	}

	PcapAddrDstAddrFID = (*env)->GetFieldID(env, PcapAddrClass, "dstaddr", "Lcom/ardikars/jxnet/SockAddr;");

	if(PcapAddrDstAddrFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapAddr.dstaddr:SockAddr");
		return;
	}
}

jclass SockAddrClass = NULL;
jfieldID SockAddrSaFamilyFID = NULL;
jfieldID SockAddrDataFID = NULL;

void SetSockAddrIDs(JNIEnv *env) {

	SockAddrClass = (*env)->FindClass(env, "com/ardikars/jxnet/SockAddr");

	if(SockAddrClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.SockAddr");
		return;
	}

	SockAddrSaFamilyFID = (*env)->GetFieldID(env, SockAddrClass, "sa_family", "S");

	if(SockAddrSaFamilyFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field SockAddr.sa_family:short");
		return;
	}

	SockAddrDataFID = (*env)->GetFieldID(env, SockAddrClass, "data", "[B");

	if(SockAddrDataFID== NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field SockAddr.data:byte[]");
		return;
	}
}

jclass PcapClass = NULL;
jfieldID PcapAddressFID = NULL;
jmethodID PcapGetAddressMID = NULL;

void SetPcapIDs(JNIEnv *env) {

  	PcapClass = (*env)->FindClass(env, "com/ardikars/jxnet/Pcap");

  	if (PcapClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.Pcap");
		return;
	}

	PcapAddressFID = (*env)->GetFieldID(env, PcapClass, "address", "J");

	if (PcapAddressFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Pcap.address:long");
		return;
	}

	PcapGetAddressMID = (*env)->GetMethodID(env, PcapClass, "getAddress", "()J");

	if (PcapGetAddressMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method Pcap.getAddress(long)");
		return;
	}
}

jclass FileClass = NULL;
jfieldID FileAddressFID = NULL;
jmethodID FileGetAddressMID = NULL;

void SetFileIDs(JNIEnv *env) {

	FileClass = (*env)->FindClass(env, "com/ardikars/jxnet/File");

	if (FileClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.File");
		return;
	}

	FileAddressFID = (*env)->GetFieldID(env, FileClass, "address", "J");

	if (FileAddressFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field File.address:long");
		return;
	}

	FileGetAddressMID = (*env)->GetMethodID(env, FileClass, "getAddress", "()J");

	if (FileGetAddressMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method File.getAddress(long)");
		return;
	}

}

jclass PcapPktHdrClass = NULL;
jfieldID PcapPktHdrCaplenFID = NULL;
jfieldID PcapPktHdrLenFID = NULL;
jfieldID PcapPktHdrTvSecFID = NULL;
jfieldID PcapPktHdrTvUsecFID = NULL;
jmethodID PcapPktHdrNewInstance = NULL;

void SetPcapPktHdrIDs(JNIEnv *env) {

	PcapPktHdrClass = (*env)->FindClass(env, "com/ardikars/jxnet/PcapPktHdr");

	if(PcapPktHdrClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.PcapPktHdr");
		return;
	}

	PcapPktHdrCaplenFID = (*env)->GetFieldID(env, PcapPktHdrClass, "caplen", "I");

	if(PcapPktHdrCaplenFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapPktHdr.caplen:int");
		return;
	}

	PcapPktHdrLenFID = (*env)->GetFieldID(env, PcapPktHdrClass, "len", "I");

	if(PcapPktHdrLenFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapPktHdr.len:int");
		return;
	}

	PcapPktHdrTvSecFID = (*env)->GetFieldID(env, PcapPktHdrClass, "tv_sec", "I");

	if(PcapPktHdrTvSecFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapPktHdr.tv_sec:int");
		return;
	}

	PcapPktHdrTvUsecFID = (*env)->GetFieldID(env, PcapPktHdrClass, "tv_usec", "J");

	if(PcapPktHdrTvUsecFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapPktHdr.tv_usec:long");
		return;
	}

	PcapPktHdrNewInstance = (*env)->GetStaticMethodID(env, PcapPktHdrClass, "newInstance", "(IIIJ)Lcom/ardikars/jxnet/PcapPktHdr;");

	if(PcapPktHdrNewInstance == NULL) {
	    ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method PcapPktHdr.newInstance(int,int,int,long)");
	    return;
	}

}

jclass ByteBufferClass = NULL;
jmethodID ByteBufferClearMID = NULL;
jmethodID ByteBufferPutMID = NULL;

void SetByteBufferIDs(JNIEnv *env) {

	ByteBufferClass = (*env)->FindClass(env, "java/nio/ByteBuffer");

	if(ByteBufferClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class java.nio.ByteBuffer");
		return;
	}

	ByteBufferClearMID = (*env)->GetMethodID(env, ByteBufferClass, "clear", "()Ljava/nio/Buffer;");

	if(ByteBufferClearMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method ByteBuffer.clear()");
		return;
	}

	ByteBufferPutMID = (*env)->GetMethodID(env, ByteBufferClass, "put", "(Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;");

	if(ByteBufferPutMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method ByteBuffer.put(ByteBuffer)");
  		return;
	}
}

jclass PcapDumperClass = NULL;
jfieldID PcapDumperAddressFID = NULL;
jmethodID PcapDumperGetAddressMID = NULL;

void SetPcapDumperIDs(JNIEnv *env) {

	PcapDumperClass = (*env)->FindClass(env, "com/ardikars/jxnet/PcapDumper");

	if (PcapDumperClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.PcapDumper");
		return;
	}

	PcapDumperAddressFID = (*env)->GetFieldID(env, PcapDumperClass, "address", "J");

	if (PcapDumperAddressFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapDumper.address:long");
		return;
	}

	PcapDumperGetAddressMID = (*env)->GetMethodID(env, PcapDumperClass, "getAddress", "()J");

	if (PcapDumperGetAddressMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method PcapDumper.getAddress(long)");
		return;
	}
}

jclass BpfProgramClass = NULL;
jfieldID BpfProgramAddressFID = NULL;
jmethodID BpfProgramGetAddressMID = NULL;

void SetBpfProgramIDs(JNIEnv *env) {

	BpfProgramClass = (*env)->FindClass(env, "com/ardikars/jxnet/BpfProgram");

	if (BpfProgramClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.BpfProgram");
		return;
	}

	BpfProgramAddressFID = (*env)->GetFieldID(env, BpfProgramClass, "address", "J");

	if (BpfProgramAddressFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field BpfProgram.address:long");
		return;
	}

	BpfProgramGetAddressMID = (*env)->GetMethodID(env, BpfProgramClass, "getAddress", "()J");

	if (BpfProgramGetAddressMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method BpfProgram.getAddress(long)");
		return;
	}

}

jclass PcapStatClass = NULL;
jfieldID PcapStatPsRecvFID = NULL;
jfieldID PcapStatPsDropFID = NULL;
jfieldID PcapStatPsIfDropFID = NULL;

void SetPcapStatIDs(JNIEnv *env) {

	PcapStatClass = (*env)->FindClass(env, "com/ardikars/jxnet/PcapStat");

	if(PcapStatClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.PcapStat");
		return;
	}

	PcapStatPsRecvFID = (*env)->GetFieldID(env, PcapStatClass, "ps_recv", "J");

	if(PcapStatPsRecvFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapStat.ps_recv:long");
		return;
	}

	PcapStatPsDropFID = (*env)->GetFieldID(env, PcapStatClass, "ps_drop", "J");

	if(PcapStatPsDropFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapStat.ps_drop:long");
		return;
	}

	PcapStatPsIfDropFID = (*env)->GetFieldID(env, PcapStatClass, "ps_ifdrop", "J");

	if(PcapStatPsDropFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapStat.ps_ifdrop:long");
		return;
	}
}

jclass Inet4AddressClass = NULL;
jmethodID Inet4AddressValueOfMID = NULL;
jmethodID Inet4AddressToIntMID = NULL;
jfieldID Inet4AddressAddressFID = NULL;

void SetInet4AddressIDs(JNIEnv *env) {

	Inet4AddressClass = (*env)->FindClass(env, "com/ardikars/jxnet/Inet4Address");

	if(Inet4AddressClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.Inet4Address");
		return;
	}

	Inet4AddressValueOfMID = (*env)->GetStaticMethodID(env, Inet4AddressClass, "valueOf", "(I)Lcom/ardikars/jxnet/Inet4Address;");

	if(Inet4AddressValueOfMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method Inet4Address.valueOf(int)");
		return;
	}

	Inet4AddressToIntMID = (*env)->GetMethodID(env, Inet4AddressClass, "toInt", "()I");

	if(Inet4AddressToIntMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method Inet4Address.toInt()");
		return;
	}

	Inet4AddressAddressFID = (*env)->GetFieldID(env, Inet4AddressClass, "address", "[B");

	if (Inet4AddressAddressFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Inet4Address.address");
		return;
	}
}

jclass PcapDirectionClass = NULL;
jmethodID PcapDirectionNameMID = NULL;

void SetPcapDirectionIDs(JNIEnv *env) {

	PcapDirectionClass = (*env)->FindClass(env, "com/ardikars/jxnet/PcapDirection");

	if(PcapDirectionClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.PcapDirection");
   		return;
  	}

  	PcapDirectionNameMID = (*env)->GetMethodID(env, PcapDirectionClass, "name", "()Ljava/lang/String;");

  	if(PcapDirectionNameMID == NULL) {
  		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method PcapDirection.name()");
  		return;
	}
}

jclass MacAddressClass = NULL;
jmethodID MacAddressValueOfMID = NULL;

void SetMacAddressIDs(JNIEnv *env) {

	MacAddressClass = (*env)->FindClass(env, "com/ardikars/jxnet/MacAddress");

	if (MacAddressClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.MacAddress");
		return;
	}

	MacAddressValueOfMID = (*env)->GetStaticMethodID(env, MacAddressClass, "valueOf", "([B)Lcom/ardikars/jxnet/MacAddress;");

	if(MacAddressValueOfMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method MacAddress.valueOf(byte[])");
		return;
	}
}
