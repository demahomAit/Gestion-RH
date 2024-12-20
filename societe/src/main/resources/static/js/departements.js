const API_BASE_URL = "http://localhost:8080/api";

// Fonction pour récupérer la liste des départements
function fetchDepartements() {
    axios.get(`${API_BASE_URL}/departements`)
        .then(response => {
            const departements = response.data;
            const departementsTable = document.getElementById("departements-table");
            departementsTable.innerHTML = "";

            departements.forEach(departement => {
                const row = `
                    <tr>
                        <td>${departement.iddept}</td>
                        <td>${departement.nomdept}</td>
                        <td>
                            <button class="btn btn-danger btn-sm" onclick="deleteDepartement('${departement.iddept}')">Supprimer</button>
                        </td>
                    </tr>
                `;
                departementsTable.innerHTML += row;
            });
        })
        .catch(error => {
            console.error("Erreur lors de la récupération des départements :", error);
        });
}

// Ajouter un département
document.getElementById("add-departement-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const iddept = document.getElementById("iddept").value;
    const nomdept = document.getElementById("nomdept").value;

    const newDepartement = {
        iddept: iddept,
        nomdept: nomdept
    };

    axios.post(`${API_BASE_URL}/departements`, newDepartement)
        .then(response => {
            alert("Département ajouté avec succès !");
            fetchDepartements();
        })
        .catch(error => {
            console.error("Erreur lors de l'ajout du département :", error);
        });
});

// Supprimer un département
function deleteDepartement(departementId) {
    if (confirm("Êtes-vous sûr de vouloir supprimer ce département ?")) {
        axios.delete(`${API_BASE_URL}/departements/${departementId}`)
            .then(response => {
                alert("Département supprimé avec succès !");
                fetchDepartements();
            })
            .catch(error => {
                console.error("Erreur lors de la suppression du département :", error);
            });
    }
}

// Afficher les employés d'un département
document.getElementById("get-employes-departement-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const departementIdOrName = document.getElementById("departementIdOrName").value;

    axios.get(`${API_BASE_URL}/employes/departement/${departementIdOrName}`)
        .then(response => {
            const departementEmployesDTO = response.data;
            const employes = departementEmployesDTO.employes;
            const employesTable = document.getElementById("employes-departement-table");
            employesTable.innerHTML = ""; // Vider la table avant de remplir

            employes.forEach(employe => {
                const row = `
                    <tr>
                        <td>${employe.id}</td>
                        <td>${employe.nomEmp}</td>
                        <td>${employe.salaire}</td>
                    </tr>
                `;
                employesTable.innerHTML += row;
            });
        })
        .catch(error => {
            if (error.response && error.response.status === 404) {
                alert("Département non trouvé. Veuillez vérifier l'ID ou le nom du département.");
            } else {
                console.error("Erreur lors de la récupération des employés du département :", error);
            }
        });
});
// Afficher l'historique des affectations d'un département
document.getElementById("get-historique-departement-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const departementId = document.getElementById("historiqueDepartementId").value;

    axios.get(`${API_BASE_URL}/historique/departement/${departementId}`)
        .then(response => {
            const historique = response.data;
            const historiqueTable = document.getElementById("historique-departement-table");
            historiqueTable.innerHTML = ""; // Vider la table avant de remplir

            historique.forEach(affectation => {
                const row = `
                    <tr>
                        <td>${affectation.id}</td>
                        <td>${affectation.employe.nomEmp}</td>
                        <td>${affectation.departementPrecedent.nomdept}</td>
                        <td>${affectation.nouveauDepartement.nomdept}</td>
                        <td>${affectation.dateAffectation}</td>
                    </tr>
                `;
                historiqueTable.innerHTML += row;
            });
        })
        .catch(error => {
            if (error.response && error.response.status === 404) {
                alert("Aucun historique trouvé pour ce département.");
            } else {
                console.error("Erreur lors de la récupération de l'historique :", error);
            }
        });
});
// Charger les données au chargement de la page
fetchDepartements();