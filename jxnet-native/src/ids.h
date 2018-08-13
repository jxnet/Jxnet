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

extern jclass StringBuilderClass;
extern jmethodID StringBuilderSetLengthMID;
extern jmethodID StringBuilderAppendMID;

void SetStringBuilderIDs(JNIEnv *env);

extern jclass ListClass;
extern jmethodID ListAddMID;

void SetListIDs(JNIEnv *env);

extern jclass PcapIfClass;
extern jfieldID PcapIfNameFID;
extern jfieldID PcapIfDescriptionFID;
extern jfieldID PcapIfAddressesFID;
extern jfieldID PcapIfFlagsFID;

void SetPcapIfIDs(JNIEnv *env);

extern jclass PcapAddrClass;
extern jfieldID PcapAddrAddrFID;
extern jfieldID PcapAddrNetmaskFID;
extern jfieldID PcapAddrBroadAddrFID;
extern jfieldID PcapAddrDstAddrFID;

void SetPcapAddrIDs(JNIEnv *env);

extern jclass FileClass;
extern jfieldID FileAddressFID;
extern jmethodID FileGetAddressMID;

void SetFileIDs(JNIEnv *env);

extern jclass SockAddrClass;
extern jfieldID SockAddrSaFamilyFID;
extern jfieldID SockAddrDataFID;

void SetSockAddrIDs(JNIEnv *env);

extern jclass PcapClass;
extern jfieldID PcapAddressFID;
extern jfieldID PcapIsDeadFID;
extern jmethodID PcapGetAddressMID;
extern jmethodID PcapIsDeadMID;

void SetPcapIDs(JNIEnv *env);

extern jclass PcapPktHdrClass;
extern jfieldID PcapPktHdrCaplenFID;
extern jfieldID PcapPktHdrLenFID;
extern jfieldID PcapPktHdrTvSecFID;
extern jfieldID PcapPktHdrTvUsecFID;
extern jmethodID PcapPktHdrNewInstance;

void SetPcapPktHdrIDs(JNIEnv *env);

extern jclass ByteBufferClass;
extern jmethodID ByteBufferClearMID;
extern jmethodID ByteBufferPutMID;

void SetByteBufferIDs(JNIEnv *env);

extern jclass PcapDumperClass;
extern jfieldID PcapDumperAddressFID;
extern jmethodID PcapDumperGetAddressMID;

void SetPcapDumperIDs(JNIEnv *env);

extern jclass BpfProgramClass;
extern jfieldID BpfProgramAddressFID;
extern jmethodID BpfProgramGetAddressMID;

void SetBpfProgramIDs(JNIEnv *env);

extern jclass PcapStatClass;
extern jfieldID PcapStatPsRecvFID;
extern jfieldID PcapStatPsDropFID;
extern jfieldID PcapStatPsIfDropFID;

void SetPcapStatIDs(JNIEnv *env);

extern jclass Inet4AddressClass;
extern jmethodID Inet4AddressValueOfMID;
extern jmethodID Inet4AddressToIntMID;
extern jfieldID Inet4AddressAddressFID;

void SetInet4AddressIDs(JNIEnv *env);

extern jclass PcapDirectionClass;
extern jmethodID PcapDirectionNameMID;

void SetPcapDirectionIDs(JNIEnv *env);

extern jclass MacAddressClass;
extern jmethodID MacAddressValueOfMID;

void SetMacAddressIDs(JNIEnv *env);
