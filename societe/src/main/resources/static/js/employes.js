const API_BASE_URL = "http://localhost:8080/api";

// Fonction pour récupérer la liste des employés
function fetchEmployes() {
    axios.get(`${API_BASE_URL}/employes`)
        .then(response => {
            const employes = response.data;
            const employesTable = document.getElementById("employes-table");
            employesTable.innerHTML = "";

            employes.forEach(employe => {
                const row = `
                    <tr>
                        <td>${employe.id}</td>
                        <td>${employe.nomEmp}</td>
                        <td>${employe.salaire}</td>
                        <td>${employe.refDeptId}</td>
                        <td>
                            <button class="btn btn-danger btn-sm" onclick="deleteEmploye(${employe.id})">Supprimer</button>
                        </td>
                    </tr>
                `;
                employesTable.innerHTML += row;
            });
        })
        .catch(error => {
            console.error("Erreur lors de la récupération des employés :", error);
        });
}

// Ajouter un employé
document.getElementById("add-employe-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const nomEmp = document.getElementById("nomEmp").value;
    const salaire = document.getElementById("salaire").value;
    const refDeptId = document.getElementById("refDeptId").value;

    const newEmploye = {
        nomEmp: nomEmp,
        salaire: parseFloat(salaire),
        refDeptId: refDeptId
    };

    axios.post(`${API_BASE_URL}/employes/add`, newEmploye)
        .then(response => {
            alert("Employé ajouté avec succès !");
            fetchEmployes();
        })
        .catch(error => {
            console.error("Erreur lors de l'ajout de l'employé :", error);
        });
});

// Modifier un employé
document.getElementById("update-employe-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const employeId = document.getElementById("updateEmployeId").value;
    const nomEmp = document.getElementById("updateNomEmp").value;
    const salaire = document.getElementById("updateSalaire").value;
    const refDeptId = document.getElementById("updateRefDeptId").value;

    const updatedEmploye = {
        nomEmp: nomEmp,
        salaire: parseFloat(salaire),
        refDeptId: refDeptId
    };

    axios.put(`${API_BASE_URL}/employes/update/${employeId}`, updatedEmploye)
        .then(response => {
            alert("Employé mis à jour avec succès !");
            fetchEmployes();
        })
        .catch(error => {
            console.error("Erreur lors de la mise à jour de l'employé :", error);
        });
});

// Supprimer un employé
function deleteEmploye(employeId) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cet employé ?")) {
        axios.delete(`${API_BASE_URL}/employes/delete/${employeId}`)
            .then(response => {
                alert("Employé supprimé avec succès !");
                fetchEmployes();
            })
            .catch(error => {
                console.error("Erreur lors de la suppression de l'employé :", error);
            });
    }
}

// Réaffecter un employé
document.getElementById("reaffecter-employe-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const employeId = document.getElementById("employeId").value;
    const nouveauDepartementId = document.getElementById("nouveauDepartementId").value;

    axios.put(`${API_BASE_URL}/employes/reaffecter/${employeId}/${nouveauDepartementId}`)
        .then(response => {
            alert("Employé réaffecté avec succès !");
            fetchEmployes();
        })
        .catch(error => {
            console.error("Erreur lors de la réaffectation de l'employé :", error);
        });
});

// Afficher l'historique des affectations d'un employé
document.getElementById("get-historique-employe-form").addEventListener("submit", function(event) {
    event.preventDefault();

    const employeId = document.getElementById("historiqueEmployeId").value;

    axios.get(`${API_BASE_URL}/historique/employe/${employeId}`)
        .then(response => {
            const historique = response.data;
            const historiqueTable = document.getElementById("historique-employe-table");
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
                alert("Aucun historique trouvé pour cet employé.");
            } else {
                console.error("Erreur lors de la récupération de l'historique :", error);
            }
        });
});

// Charger les données au chargement de la page
fetchEmployes();