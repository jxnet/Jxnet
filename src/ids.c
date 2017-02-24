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

#include "../src/utils.h"

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

jclass AddrClass = NULL;
jfieldID AddrAddrTypeFID = NULL;
jfieldID AddrAddrBitsFID = NULL;
jfieldID AddrDataFID = NULL;
jmethodID AddrInitializeMID = NULL;
jmethodID AddrGetStringAddressMID = NULL;

void SetAddrIDs(JNIEnv *env) {
    AddrClass = (*env)->FindClass(env, "com/ardikars/jxnet/Addr");
    if(AddrClass == NULL) {
        ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.Addr");
   		return;
    } 
    AddrAddrTypeFID = (*env)->GetFieldID(env, AddrClass, "addr_type", "S");
  	if(AddrAddrTypeFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Addr.addr_type:short");
		return;
	}
    AddrAddrBitsFID = (*env)->GetFieldID(env, AddrClass, "addr_bits", "S");
  	if(AddrAddrBitsFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Addr.addr_bits:short");
		return;
	}
    AddrDataFID = (*env)->GetFieldID(env, AddrClass, "data", "[B");
  	if(AddrDataFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Addr.data:byte[]");
		return;
	}
    AddrInitializeMID = (*env)->GetStaticMethodID(env, AddrClass, "initialize", "(SS[B)Lcom/ardikars/jxnet/Addr;");
    if (AddrInitializeMID == NULL) {
        ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize static method Addr.initialize(short,short)");
		return;
    }
    AddrGetStringAddressMID = (*env)->GetMethodID(env, AddrClass, "getStringAddress", "()Ljava/lang/String;");
    if (AddrGetStringAddressMID == NULL) {
        ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method Addr.getStringAddress()");
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
	//puts("Set PcapIf IDs");
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
	//puts("Set PcapAddr IDs");
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
	//puts("Set SockAddr IDs");
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

jclass PointerClass = NULL;
jfieldID PointerAddressFID = NULL;

void SetPointerIDs(JNIEnv *env) {
	//puts("Set Pointer IDs");
  	PointerClass = (*env)->FindClass(env, "com/ardikars/jxnet/util/Pointer");
  	if(PointerClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.util.Pointer");
		return;
	}
	PointerAddressFID = (*env)->GetFieldID(env, PointerClass, "address", "J");
	if(PointerAddressFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Pointer.address:long");
		return;
	}
}

jclass PcapClass = NULL;
jfieldID PcapPointerFID = NULL;

void SetPcapIDs(JNIEnv *env) {
	//puts("Set Pcap IDs");
  	PcapClass = (*env)->FindClass(env, "com/ardikars/jxnet/Pcap");
  	if(PcapClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.Pcap");
		return;
	}
	PcapPointerFID = (*env)->GetFieldID(env, PcapClass, "pointer", "Lcom/ardikars/jxnet/util/Pointer;");
	if(PcapPointerFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Pcap.pointer:Pointer");
		return;
	}
}

jclass ArpClass = NULL;
jfieldID ArpPointerFID = NULL;

void SetArpIDs(JNIEnv *env) {

  	ArpClass = (*env)->FindClass(env, "com/ardikars/jxnet/Arp");
  	if(ArpClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.Arp");
		return;
	}
	ArpPointerFID = (*env)->GetFieldID(env, ArpClass, "pointer", "Lcom/ardikars/jxnet/util/Pointer;");
	if(ArpPointerFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field Pcap.pointer:Pointer");
		return;
	}
}

jclass ArpEntryClass = NULL;
jfieldID ArpEntryArpPaFID = NULL;
jfieldID ArpEntryArpHaFID = NULL;
jmethodID ArpEntryInitializeMID = NULL;

void SetArpEntryIDs(JNIEnv *env) {
    ArpEntryClass = (*env)->FindClass(env, "com/ardikars/jxnet/ArpEntry");
	if(ArpEntryClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.ArpEntry");
		return;
	}
	ArpEntryArpPaFID = (*env)->GetFieldID(env, ArpEntryClass, "arp_pa", "Lcom/ardikars/jxnet/Addr;");
	if(ArpEntryArpPaFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field ArpEntry.arp_pa:Addr");
		return;
	}
	ArpEntryArpHaFID = (*env)->GetFieldID(env, ArpEntryClass, "arp_ha", "Lcom/ardikars/jxnet/Addr;");
	if(ArpEntryArpHaFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field ArpEntry.arp_ha:Addr");
		return;
	}
    ArpEntryInitializeMID = (*env)->GetStaticMethodID(env, ArpEntryClass, "initialize", "(Lcom/ardikars/jxnet/Addr;Lcom/ardikars/jxnet/Addr;)Lcom/ardikars/jxnet/ArpEntry;");
    if(ArpEntryInitializeMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize static method ArpEntry.initialize(Addr,Addr)");
		return;
	}
}


jclass FileClass = NULL;
jfieldID FilePointerFID = NULL;

void SetFileIDs(JNIEnv *env) {
	FileClass = (*env)->FindClass(env, "com/ardikars/jxnet/File");
	if(FileClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.File");
		return;
	}
	FilePointerFID = (*env)->GetFieldID(env, FileClass, "pointer", "Lcom/ardikars/jxnet/util/Pointer;");
	if(FilePointerFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field File.pointer:Pointer");
		return;
	}
}

jclass PcapPktHdrClass = NULL;
jfieldID PcapPktHdrCaplenFID = NULL;
jfieldID PcapPktHdrLenFID = NULL;
jfieldID PcapPktHdrTvSecFID = NULL;
jfieldID PcapPktHdrTvUsecFID = NULL;

void SetPcapPktHdrIDs(JNIEnv *env) {
	//puts("Set PcapPktHdr IDs");
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
}

jclass ByteBufferClass = NULL;
jmethodID ByteBufferClearMID = NULL;
jmethodID ByteBufferPutMID = NULL;

void SetByteBufferIDs(JNIEnv *env) {
	//puts("Set ByteBuffer IDs");
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
jfieldID PcapDumperPointerFID = NULL;

void SetPcapDumperIDs(JNIEnv *env) {
	//puts("Set PcapDumper IDs");
  	PcapDumperClass = (*env)->FindClass(env, "com/ardikars/jxnet/PcapDumper");
  	if(PcapDumperClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.PcapDumper");
		return;
	}
	PcapDumperPointerFID = (*env)->GetFieldID(env, PcapDumperClass, "pointer", "Lcom/ardikars/jxnet/util/Pointer;");
	if(PcapPointerFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field PcapDumper.pointer:Pointer");
		return;
	}
}

jclass BpfProgramClass = NULL;
jfieldID BpfProgramPointerFID = NULL;

void SetBpfProgramIDs(JNIEnv *env) {
	//puts("Set BpfProgram IDs");
	BpfProgramClass = (*env)->FindClass(env, "com/ardikars/jxnet/BpfProgram");
  	if(BpfProgramClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.BpfProgram");
		return;
	}
	BpfProgramPointerFID = (*env)->GetFieldID(env, BpfProgramClass, "pointer", "Lcom/ardikars/jxnet/util/Pointer;");
	if(BpfProgramPointerFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field BpfProgram.pointer:Pointer");
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
jmethodID Inet4AddressUpdateMID = NULL;

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
	Inet4AddressUpdateMID = (*env)->GetMethodID(env, Inet4AddressClass, "update", "(Lcom/ardikars/jxnet/Inet4Address;)V");
	if(Inet4AddressToIntMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize method Inet4Address.update(Inet4Address)");
		return;
	}
}

jclass WLanClass = NULL;
jfieldID WLanDeviceFID = NULL;

void SetWLanIDs(JNIEnv *env, jobject jobj) {
	WLanClass = (*env)->GetObjectClass(env, jobj);
	if(WLanClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.util.WLan");
		return;
	}
	WLanDeviceFID = (*env)->GetFieldID(env, WLanClass, "device", "Ljava/lang/String;");
	if(WLanDeviceFID == NULL) {
		ThrowNew(env, NO_SUCH_FIELD_EXCEPTION, "Unable to initialize field WLan.device");
		return;
	}
}

jclass WLanOperationModeClass = NULL;
jmethodID WLanOperationModeInitMID = NULL;

void SetWLanDeviceIDs(JNIEnv *env) {
	WLanOperationModeClass = (*env)->FindClass(env, "com/ardikars/jxnet/util/WLanOperationMode");
	if(WLanOperationModeClass == NULL) {
		ThrowNew(env, CLASS_NOT_FOUND_EXCEPTION, "Unable to initialize class com.ardikars.jxnet.util.WLanOperationMode");
		return;
	}
	WLanOperationModeInitMID = (*env)->GetMethodID(env, WLanOperationModeClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
	if(WLanOperationModeInitMID == NULL) {
		ThrowNew(env, NO_SUCH_METHOD_EXCEPTION, "Unable to initialize contractor WLanOperationMode(String, String)");
		return;
	}	
}
