/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function displayEquipment(equipmentId, name, location, description, status, category, imgSrc, isStaff) {
    document.getElementById("editid").value = equipmentId;
    document.getElementById("editname").value = name;
    document.getElementById("editlocation").value = location;
    document.getElementById("editdescription").value = description;
    document.getElementById("editstatus").value = status;
    document.getElementById("editcategory").value = category;

    var dropdown = document.getElementById("editIsStaffDropdown");
    if (isStaff === "staff") {
        dropdown.value = "staff";
    } else {
        dropdown.value = "user";
    }
}
function deleteEquipment(equipmentId, name, location, description, status, category, imgSrc) {

    document.getElementById("id").value = equipmentId;
    document.getElementById("name").value = name;
    document.getElementById("location").value = location;
    document.getElementById("description").value = description;
    document.getElementById("status").value = status;
}

function damageReport(equipmentId, name, location, description, status, category, imgSrc) {
    console.log("Reporting damage for equipment:", equipmentId);
    document.getElementById("reportid").value = equipmentId;
    document.getElementById("reportname").value = name;
    document.getElementById("reportlocation").value = location;
    document.getElementById("reportdescription").value = description;
    document.getElementById("reportstatus").value = status;
}

function toggleView(selectedView) {
    console.log("Toggling view to:", selectedView);
    if (selectedView === "equipmentList") {
        document.getElementById("equipmentListView").style.display = "block";
        document.getElementById("returnsManagementView").style.display = "none";
    } else if (selectedView === "returnsManagement") {
        document.getElementById("equipmentListView").style.display = "none";
        document.getElementById("returnsManagementView").style.display = "block";
        loadReturnsData(); // Automatically load return data when this view is selected
    }
}
function loadEquipmentDetails(requestId) {
    $.ajax({
        url: baseUrl + '/fetchEquipmentDetails',
        type: 'GET',
        data: {requestId: requestId},
        success: function (data) {
            var equipments = JSON.parse(data);
            var tbody = $('#equipmentDetailsTable tbody');
            tbody.empty();
            equipments.forEach(function (equipment) {
                tbody.append(`<tr>
                    <td>${equipment.equipmentID}</td>
                    <td>${equipment.name}</td>
                    <td>${equipment.location}</td>
                    <td>${equipment.status}</td>
                    <td>
                        <select class="form-select">
                            <option value="available">Available</option>
                            <option value="damaged">Damaged</option>
                        </select>
                        <button class="btn btn-primary" onclick="submitDamageReport(${equipment.equipmentID})">Submit Report</button>
                    </td>
                </tr>`);
            });
            
        },
        error: function (xhr, status, error) {
            console.error('Failed to load equipment details:', error);
            console.error('Status:', status);
            console.error('Error:', xhr.responseText); // Output full error message/response from server
            alert('Failed to load equipment details: ' + xhr.responseText);
        }
    });
}

function submitDamageReport(equipmentID) {
    var status = $(`#statusSelect${equipmentID}`).val();
    var description = $(`#damageDescription${equipmentID}`).val();

    $.ajax({
        url: '/submitDamageReport',
        type: 'POST',
        data: {
            equipmentID: equipmentID,
            status: status,
            description: description
        },
        success: function (response) {
            alert('Damage report submitted successfully.');
        },
        error: function () {
            alert('Failed to submit damage report.');
        }
    });
}
 

function loadReturnsData() {
    var url = baseUrl + '/fetchEquipmentDetails';
    console.log("Request URL:", url); // Confirm the request URL

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var tbody = $('#returnsManagementView table tbody');
            tbody.empty();

            if (data.requests && data.requests.length > 0) {
                data.requests.forEach(function (request) {
                    var rows = request.equipments.map((equipment, index) => `
                        <tr>
                            ${index === 0 ? `<td rowspan="${request.equipments.length}">${request.requestID}</td>
                            <td rowspan="${request.equipments.length}">${request.requesterName}</td>
                            <td rowspan="${request.equipments.length}">${request.requestDateTime}</td>
                            <td rowspan="${request.equipments.length}">${request.status}</td>` : ''}
                            <td>${equipment.name}</td>
                            <td>${equipment.location}</td>
                            ${index === 0 ? `<td rowspan="${request.equipments.length}">
                                <button class="btn btn-primary" onclick="manageReturn(${request.requestID})">Manage Return</button>
                            </td>` : ''}
                        </tr>`).join('');

                    tbody.append(rows);
                });
            } else {
                tbody.append('<tr><td colspan="7">No return requests available.</td></tr>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Failed to load return data:", error);
            $('#returnsManagementView table tbody').append('<tr><td colspan="7">Error loading data.</td></tr>');
        }
    });
}

// 更新 manageReturn 函数来动态使能或禁用描述输入
function manageReturn(requestID) {
    console.log("Fetching data for request ID:", requestID);

    $.ajax({
        url: baseUrl + '/fetchEquipmentDetails',
        type: 'GET',
        data: {requestID: requestID},
        success: function (data) {
            console.log("Data received for manage return:", data);
            var form = $('#manageReturnForm');
            form.empty();

            var request = data.requests.find(r => r.requestID == requestID);
            if (request && request.equipments.length > 0) {
                request.equipments.forEach(equipment => {
                    form.append(`
                        <div class="form-group">
                            <label>${equipment.name} - ${equipment.location}</label>
                            <select class="form-control status-select" id="status${equipment.equipmentID}" name="status${equipment.equipmentID}">
                                <option value="available">Available</option>
                                <option value="damaged">Damaged</option>
                            </select>
                            <input type="text" class="form-control mt-2 description-input" id="description${equipment.equipmentID}" name="description${equipment.equipmentID}" placeholder="Describe damage (if any)" disabled required>
                        </div>
                    `);

                    // Add event listener to enable/disable description input based on status
                    $(`#status${equipment.equipmentID}`).change(function () {
                        if (this.value === 'damaged') {
                            $(`#description${equipment.equipmentID}`).prop('disabled', false);
                        } else {
                            $(`#description${equipment.equipmentID}`).prop('disabled', true).val('');
                        }
                    });
                });
                $('#manageReturnModal').modal('show');
            } else {
                console.error("No equipment data found for the given request ID.");
                alert('No equipment data found for this return request.');
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching data for manage return:", error);
            alert('Error fetching return data: ' + xhr.responseText);
        }
    });
}


function submitReturnManagement() {
    var formData = new FormData(document.getElementById('manageReturnForm'));
    var jsonObject = {
        requestID: formData.get('requestID'), // Assuming there's an input named 'requestID'
        damages: []
    };

    formData.forEach((value, key) => {
        if (key.startsWith('status')) {
            var equipmentID = key.substring(6); // Assuming key format is 'status123'
            var status = value;
            var description = formData.get('description' + equipmentID);
            jsonObject.damages.push({
                equipmentID: equipmentID,
                description: description,
                status: status
            });
        }
    });

    console.log("Final JSON being sent:", JSON.stringify(jsonObject));

    $.ajax({
        url: baseUrl + '/manageReturn',
        type: 'POST',
        data: JSON.stringify(jsonObject),
        contentType: 'application/json',
        success: function (response) {
            console.log('Return management submitted successfully:', response);
            $('#manageReturnModal').modal('hide');
            alert('Return management submitted successfully.');
            loadReturnsData(); // 重新加载退货管理数据
        },
        error: function (xhr, status, error) {
            console.error('Failed to submit return management:', error);
            alert('Failed to submit return management: ' + xhr.responseText);
        }
    });
}