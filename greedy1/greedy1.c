#include <stdio.h>
#include <string.h>

int main(void)
{
    char str[200];
    FILE *fp = fopen("jobs.txt","r");
    if(!fp) return 0;

    while(fgets(str, sizeof(str), fp) != NULL) {
        int len = strlen(str);
        if(str[len-1] == '\n') {
            str[len-1] = 0;
        }

        printf("%s\n", str);
    } 
    fclose(fp);
}
