/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function displayEquipment(equipmentId, name, location, description, status, category, imgSrc, isStaff) {
    console.log("Displaying equipment details:", equipmentId);
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
    console.log("Preparing to delete equipment:", equipmentId);
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

function manageReturn(requestID) {
    console.log("Fetching data for request ID:", requestID);

    $.ajax({
        url: baseUrl + '/fetchEquipmentDetails',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log("Data received for manage return:", data);
            var form = $('#manageReturnForm');
            form.empty(); // 清空现有的表单内容

            // 找到与给定 requestID 匹配的请求
            var request = data.requests.find(r => r.requestID == requestID);
            if (request && request.equipments.length > 0) {
                request.equipments.forEach(equipment => {
                    form.append(`
                        <div class="form-group">
                            <label>${equipment.name} - ${equipment.location}</label>
                            <select class="form-control" name="status${equipment.equipmentID}">
                                <option value="available">Available</option>
                                <option value="damaged">Damaged</option>
                            </select>
                            <input type="text" class="form-control mt-2" name="description${equipment.equipmentID}" placeholder="Describe damage (if any)" required>
                        </div>
                    `);
                });
                $('#manageReturnModal').modal('show');
            } else {
                console.error("No equipment data found for the given request ID:", requestID);
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
    var jsonObject = {};

    formData.forEach(function (value, key) {
        // 假设表单中的每个 key 都是唯一的
        jsonObject[key] = value;
    });

    // 转换为 JSON 字符串
    var jsonString = JSON.stringify(jsonObject);

    $.ajax({
        url: baseUrl + '/manageReturn',
        type: 'POST',
        data: jsonString,
        contentType: 'application/json', // 设置发送数据的格式为 JSON
        success: function (response) {
            // 处理响应
            console.log('Return management submitted successfully:', response);
            $('#manageReturnModal').modal('hide');
            alert('Return management submitted successfully.');
        },
        error: function (xhr, status, error) {
            console.error('Failed to submit return management:', error);
            alert('Failed to submit return management: ' + xhr.responseText);
        }
    });
}


