// fonction utilitaire : "dd-mm-aaaa" => {jour : dd , mois : mm , annee : aaaa}
const extractJourMoisAnneeFormatDDMMAAAA = (date) => {
    const jour = date.slice(0, 2);
    const mois = date.slice(3, 5);
    const annee = date.slice(6, 10);

    return {
        jour: jour,
        mois: mois,
        annee: annee,
    };
};

//

const extractJourMoisAnneeFormatAAAAMMDD = (date) => {
    // YYYY-MM-DD
    const annee = date.slice(0, 4);
    const mois = date.slice(5, 7);
    const jour = date.slice(8, 10);

    return {
        jour: jour,
        mois: mois,
        annee: annee,
    };
};

const reverseString = (str) => {
    return str.split("").reverse().join("");
};

const isDateBetween = (dateToCheck, startDate, endDate) => {
    // format string : AAAA-MM-DD
    // Convertir les dates en objets Date
    let date = new Date(dateToCheck);
    let start = new Date(startDate);
    let end = new Date(endDate);

    // Vérifier si la date est entre les deux dates données
    return date >= start && date <= end;
};

export {
    extractJourMoisAnneeFormatDDMMAAAA,
    reverseString,
    extractJourMoisAnneeFormatAAAAMMDD,
    isDateBetween,
};
