const urlAdmin = '/api/admin';

async function getAdminPage() {
    let page = await fetch(urlAdmin);

    if (page.ok) {
        let listAllUsers = await page.json();
        getTableOfAllUsers(listAllUsers);
    } else {
        alert(`Error, ${page.status}`)
    }
}

function getTableOfAllUsers(listAllUsers) {
    const tBody = document.getElementById('tbody_users');

    let tr = '';
    for (let user of listAllUsers) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(' ' + role.name.toString().replaceAll('ROLE_', ''));
        }
        tr +=
            `<tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${roles}</td>
        <td>
            <button class="btn btn-info" style="color:white" data-bs-toggle="modal"
            data-bs-target="#modal_edit"
            onclick="editModal(${user.id})">Edit</button>
        </td>
        <td>
            <button class="btn btn-danger" style="color:white" data-bs-toggle="modal"
            data-bs-target="#modal_delete"
            onclick="deleteModal(${user.id})">Delete</button>
        </td>
    </tr>`
    }
    tBody.innerHTML = tr;
}

getAdminPage();