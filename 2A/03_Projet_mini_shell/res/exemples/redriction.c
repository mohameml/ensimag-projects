#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() 
{
    int fd = open("exemple.txt", O_RDONLY); // Ouvre le fichier en lecture seule

    if (fd == -1) {
        perror("Erreur lors de l'ouverture du fichier");
        return 1;
    }

    // Utilise dup2 pour rediriger le descripteur de fichier 0 (stdin) vers fd
    if (dup2(fd, STDIN_FILENO) == -1) {
        perror("Erreur lors de la redirection de stdin");
        return 1;
    }

    // Maintenant, stdin pointe vers le même fichier que fd

    // Vous pouvez lire à partir de stdin ou fd, et cela affectera le même fichier

    close(fd);

    // Lisez depuis stdin pour vérifier la redirection
    char buffer[256];
    fgets(buffer, sizeof(buffer), stdin);
    printf("Lecture depuis stdin : %s \n", buffer);

    return 0;
}