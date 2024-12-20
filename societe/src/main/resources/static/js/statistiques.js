const API_BASE_URL = "http://localhost:8080/api";

// Fonction pour afficher les statistiques
function fetchStatistiques() {
    // Nombre total d'employés
    axios.get(`${API_BASE_URL}/employes/total`)
        .then(response => {
            document.getElementById("total-employes").textContent = response.data;
        })
        .catch(error => {
            console.error("Erreur lors de la récupération du nombre total d'employés :", error);
        });

    // Département avec le plus grand nombre d'employés
    axios.get(`${API_BASE_URL}/most-employes`) // Utilisation de l'endpoint pour obtenir le département avec le plus d'employés
        .then(response => {
            const departement = response.data;
            document.getElementById("departement-most-employes").textContent = `${departement.nomdept} (${departement.employesCount} employés)`;
        })
        .catch(error => {
            console.error("Erreur lors de la récupération du département avec le plus d'employés :", error);
        });

    // Nombre d'employés par département
    axios.get(`${API_BASE_URL}/employes-par-departement`)
        .then(response => {
            const stats = response.data;
            const statsList = document.getElementById("employes-par-departement");
            statsList.innerHTML = "";

            for (const [deptId, count] of Object.entries(stats)) {
                const li = document.createElement("li");
                li.textContent = `Département ${deptId}: ${count} employés`;
                statsList.appendChild(li);
            }
        })
        .catch(error => {
            console.error("Erreur lors de la récupération du nombre d'employés par département :", error);
        });
}

// Calculer la masse salariale
document.getElementById("masse-salariale-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const departementIdOrName = document.getElementById("departementIdOrName").value;

    axios.get(`${API_BASE_URL}/employes/masse-salariale/departement/${departementIdOrName}`)
        .then(response => {
            document.getElementById("masse-salariale-result").textContent = `Masse salariale : ${response.data} €`;
        })
        .catch(error => {
            if (error.response && error.response.status === 404) {
                alert("Département non trouvé. Veuillez vérifier l'ID ou le nom du département.");
            } else {
                console.error("Erreur lors du calcul de la masse salariale :", error);
            }
        });
});

// Charger les données au chargement de la page
fetchStatistiques();