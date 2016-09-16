
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */
 
#include "../src/ids.h"
#include "../include/jxnet/com_ardikars_jxnet_util_WLan_WLanOperationMode.h"
#include "../include/jxnet/com_ardikars_jxnet_util_WLan.h"

#include <jni.h>
#include <stdlib.h>
#include <stdio.h>

#ifdef WIN32
#include <windows.h>
#include <wlanapi.h>
#include <tchar.h>
#include <conio.h>
#else
// 
#endif

#ifdef UNICODE
#define MAKEINTRESOURCEA_T(a, u) MAKEINTRESOURCEA(u)
#else
#define MAKEINTRESOURCEA_T(a, u) MAKEINTRESOURCEA(a)
#endif

#ifdef WIN32
BOOL GetGUID(const char *device, GUID guid) {
	
	BOOL r = FALSE;
	
	TCHAR buf[256];
	_stprintf_s(buf, 256, _T("{%s}"), device);
	
	HMODULE hModule;
	LPCSTR lpProcName;
	
	typedef BOOL(WINAPI *LPFN_GUIDFromString)(LPCTSTR, LPGUID);
	LPFN_GUIDFromString pGUIDFromString = NULL;
	
	hModule = LoadLibrary(TEXT("shell32.dll"));
	lpProcName = MAKEINTRESOURCEA_T(703, 704);
	if (hModule) {
		pGUIDFromString = (LPFN_GUIDFromString)GetProcAddress(hModule, lpProcName);
		if (pGUIDFromString) {
			r = pGUIDFromString((LPCTSTR) buf, (LPGUID) guid);
		}
		FreeLibrary(hModule);
	}
	if (!pGUIDFromString) {
		hModule = LoadLibrary(TEXT("Shlwapi.dll"));
		if (hModule) {
			lpProcName = MAKEINTRESOURCEA_T(269, 270);
			pGUIDFromString = (LPFN_GUIDFromString)GetProcAddress(hModule, lpProcName);
			if (pGUIDFromString) {
				r = pGUIDFromString((LPCTSTR) buf, (LPGUID) guid);
			}
			FreeLibrary(hModule);
		}
	}
	return r;
}
#endif /* WIN32 */

/*
 * Class:     com_ardikars_jxnet_util_WLan
 * Method:    SetOperationMode
 * Signature: (Lcom/ardikars/jxnet/util/WLan/WLanOperationMode;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_ardikars_jxnet_util_WLan_SetOperationMode
  (JNIEnv *env, jobject jobj, jobject joperation_mode) {
    SetWLanIDs(env, jobj);
    const char *device = (*env)->GetObjectField(env, jobj, WLanDeviceFID);
    const char *mode = (*env)->GetStringUTFChars(env, joperation_mode, 0);
#ifdef WIN32
	DWORD winRet = 0;
	DWORD dwClientVersion = 2; // 2 = for Vista & Server 2008, 1 = for XP
	PVOID pReserved = NULL; // Default
	PDWORD pdwNegotiatedVersion;
	PHANDLE phClientHandle;

	winRet = WlanOpenHandle(dwClientVersion, pReserved, &pdwNegotiatedVersion, &phClientHandle);
	
	HANDLE hClientHandle = (HANDLE) phClientHandle;
	const GUID *pInterfaceGuid = NULL;
	WLAN_INTF_OPCODE OpCode = wlan_intf_opcode_current_operation_mode; // default
	DWORD dwDataSize;
	const PVOID pData = NULL;
	
	pReserved = NULL;

	if (_tcscmp(_T("managed"), mode) == 0) {
		OpCode = DOT11_OPERATION_MODE_EXTENSIBLE_STATION;
	} else if(_tcscmp(_T("monitor"), mode) == 0) {
		OpCode = DOT11_OPERATION_MODE_NETWORK_MONITOR;
	} else {
		printf("Error: SetWlanOperationMode error, unknown mode: %s\n", mode);
	}
	
	if(GetGUID(device, &mode)) {
		winRet = WlanSetInterface(hClientHandle, pInterfaceGuid, OpCode, sizeof(dwDataSize), &pData, pReserved);
	} else {
		winRet = !ERROR_SUCCESS;
	}
	pReserved = NULL;
//	WlanCloseHandle((Handle) dwClientVersion, pReserved);
//	WlanCloseHandle(hClientHandle, pReserved);
	
	if(winRet != ERROR_SUCCESS) {
		return JNI_FALSE;
	} else {
		return JNI_TRUE;
	}
#else
	
#endif
  	return JNI_FALSE;
  }

/*
 * Class:     com_ardikars_jxnet_util_WLan
 * Method:    GetOperationMode
 * Signature: ()Lcom/ardikars/jxnet/util/WLan/WLanOperationMode;
 */
JNIEXPORT jobject JNICALL Java_com_ardikars_jxnet_util_WLan_GetOperationMode
  (JNIEnv *env, jobject jobj) {
  	SetWLanIDs(env, jobj);
  	return NULL;
  }

/*
 * Class:     com_ardikars_jxnet_util_WLan
 * Method:    IsWLan
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_ardikars_jxnet_util_WLan_IsWLan
  (JNIEnv *env, jobject jobj) {
  	SetWLanIDs(env, jobj);
  	return JNI_FALSE;
  }
  
