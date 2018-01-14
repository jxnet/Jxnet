/**
 * Copyright (C) 2018  Ardika Rommy Sanjaya
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

#include <stdio.h>
#include <stdlib.h>
#include <pcap.h>

int datalink = 1;
int snaplen = 65535;
int buffer_size = 255;
int promisc = 1;
char errbuf[PCAP_ERRBUF_SIZE];
const char *source = "wlp2s0";

int main(void) {
//  pcap_can_set_rfmon(pcap);
//  pcap_getnonblock(pcap, errbuf);

//    pcap_t *pcap = pcap_open_live(source, snaplen, pcap, 2000, errbuf);

//    int *list_dtl;
//    int count = pcap_list_datalinks(pcap, &list_dtl);
//    int i;
//    for (i=0; i<count; i++) {
//        printf("%d\n", list_dtl[i]);
//    }
//    pcap_free_datalinks(list_dtl);

//    int *list_tstamp_type;
//    int count = pcap_list_tstamp_types(pcap, &list_tstamp_type);
//    int i;
//    for (i=0; i<count; i++) {
//        printf("Timestamp type: %d\n", list_tstamp_type[i]);
//    }
//    pcap_free_tstamp_types(list_tstamp_type);

//    pcap_close(pcap);
    printf("%s\n", pcap_lib_version());
    return 0;
}
