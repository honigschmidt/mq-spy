function main() {

    var defaultQueueList = [
        "DEV.QUEUE.1",
        "DEV.QUEUE.2",
        "DEV.QUEUE.3"
    ]

    var defaultQueryStats = {
        "queryId": "0000000000000",
        "environment": "DEV",
        "searchParameters": ["Lorem ipsum"],
        "timeStampFrom": null,
        "timeStampTo": null,
        "searchParametersAndRelation": false,
        "getOnlyQueueDepth": false,
        "queryStartTime": "1970-01-01T00:00:00.000000",
        "queryEndTime": "1970-01-01T00:00:00.000000",
        "queryStatsEntries": [
            {
                "queryId": "0000000000000",
                "queueName": "DEV.QUEUE.1",
                "queueManagerName": "QM1",
                "queueDepth": 1,
                "matchingMessagesCount": 1
            },
            {
                "queryId": "0000000000000",
                "queueName": "DEV.QUEUE.2",
                "queueManagerName": "QM1",
                "queueDepth": 1,
                "matchingMessagesCount": 1
            },
            {
                "queryId": "0000000000000",
                "queueName": "DEV.QUEUE.3",
                "queueManagerName": "QM1",
                "queueDepth": 1,
                "matchingMessagesCount": 1
            }
        ]
    }

    var defaultResults = [
        {
            "putTimeStamp": "1970-01-01T00:00:00.000000",
            "sourceQueue": "DEV.QUEUE.1",
            "sourceQueueManager": "QM1",
            "messageId": "000000000000000000000000000000000000000000000001",
            "correlationId": "000000000000000000000000000000000000000000000001",
            "messagePayload": "<?xml version=\"1.0\" encoding=\"UTF-8\"?><lorem_ipsum><paragraph><sentence>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</sentence><sentence>Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</sentence><sentence>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</sentence></paragraph><paragraph><sentence>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</sentence><sentence>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</sentence></paragraph><paragraph><sentence>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</sentence><sentence>Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante.</sentence><sentence>Donec eu libero sit amet quam egestas semper.</sentence><sentence>Aenean ultricies mi vitae est.</sentence><sentence>Mauris placerat eleifend leo.</sentence></paragraph></lorem_ipsum>"
        },
        {
            "putTimeStamp": "1970-01-01T00:00:00.000000",
            "sourceQueue": "DEV.QUEUE.2",
            "sourceQueueManager": "QM1",
            "messageId": "000000000000000000000000000000000000000000000002",
            "correlationId": "000000000000000000000000000000000000000000000002",
            "messageHeader": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "messagePayload": "<?xml version=\"1.0\" encoding=\"UTF-8\"?><lorem_ipsum><paragraph><sentence>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</sentence><sentence>Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</sentence><sentence>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</sentence></paragraph><paragraph><sentence>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</sentence><sentence>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</sentence></paragraph><paragraph><sentence>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</sentence><sentence>Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit amet, ante.</sentence><sentence>Donec eu libero sit amet quam egestas semper.</sentence><sentence>Aenean ultricies mi vitae est.</sentence><sentence>Mauris placerat eleifend leo.</sentence></paragraph></lorem_ipsum>"
        },
        {
            "putTimeStamp": "1970-01-01T00:00:00.000000",
            "sourceQueue": "DEV.QUEUE.3",
            "sourceQueueManager": "QM1",
            "messageId": "000000000000000000000000000000000000000000000003",
            "correlationId": "000000000000000000000000000000000000000000000003",
            "messageHeader": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "messagePayload": "{\"sentences\":{\"sentence_1\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit.\", \"sentence_2\": \"Curabitur sagittis, lorem vel fermentum mollis, leo eros fermentum tortor, eget pulvinar augue nunc sed turpis.\", \"sentence_3\": \"Donec pretium lacus feugiat, interdum neque eu, vehicula tellus.\"}}"

        }
    ];

    const applicationName = "MQ-Spy";
    const applicationVersion = "v1.0.0";
    const resultsRootElement = document.querySelector("#results_root_element");
    const input_qmname_testqm = document.querySelector("#input_qmname_testqm");
    const btn_testqm = document.querySelector("#btn_testqm");
    const browseqsRootElement = document.querySelector("#browseqs_root_element");
    const select_environment = document.querySelector("#select_environment");
    const input_qname_browseqs = document.querySelector("#input_qname_browseqs");
    const input_ts_from = document.querySelector("#input_ts_from");
    const btn_saveqlist = document.querySelector("#btn_saveqlist");
    const input_ts_to = document.querySelector("#input_ts_to");
    const input_searchParameter1 = document.querySelector("#input_searchparameter_1");
    const input_searchParameter2 = document.querySelector("#input_searchparameter_2");
    const input_searchParameter3 = document.querySelector("#input_searchparameter_3");
    const input_searchParameter4 = document.querySelector("#input_searchparameter_4");
    const input_searchParametersAndRelation = document.querySelector("#search_params_and_rel");
    const input_getOnlyQueueDepth = document.querySelector("#get_only_queuedepth");
    const btn_browseqs = document.querySelector("#btn_browseqs");
    const btn_clear_browseqs = document.querySelector("#btn_clear_browseqs");
    const fileSelector = document.querySelector("#file_selector");
    const queryStatusRootElement = document.querySelector("#querystatus_root_element");
    const queryStatsRootElement = document.querySelector("#querystats_root_element");
    const zipFileName = "Messages.zip";
    
    var downloadList = [];
    var queueNames = []; // Queue name on query list
    var queueList = ""; // Queue names from backend
    var filteredList = [];
    var queryId = null;
    var defaultEnvList = ["DEV"];
    var test = false;  // If true load sample messages
    var queryResults = defaultResults;
    
    document.querySelector("#app_title").textContent = `${applicationName} ${applicationVersion}`;

    getenvlist();
    
    browseqsRootElement.appendChild(createQueryTable());

    if (test) {
        queryId = "0000000000000";
        queryStatsRootElement.appendChild(createQueryStatsTable(defaultQueryStats));
        resultsRootElement.appendChild(createResultsTable(defaultResults));
        let statusDefaultMessage = document.createElement("div");
        statusDefaultMessage.textContent = "Query finished."
        queryStatusRootElement.classList.add("system-message");
        queryStatusRootElement.appendChild(statusDefaultMessage);
    } else {
        let statInitMessage = document.createElement("div");
        statInitMessage.textContent = "No log yet, please start a query.";
        statInitMessage.classList.add("system-message");
        queryStatsRootElement.appendChild(statInitMessage);

        let statusInitMessage = document.createElement("div");
        statusInitMessage.textContent = "No status yet, please start a query."
        queryStatusRootElement.classList.add("system-message");
        queryStatusRootElement.appendChild(statusInitMessage);

        let resultsInitMessage = document.createElement("div");
        resultsInitMessage.textContent = "No results yet, please start a query."
        resultsInitMessage.classList.add("system-message");
        resultsRootElement.appendChild(resultsInitMessage);
    }

    function attachAutoComplete(queueList) {
        $("#input_qname_getqdepth").autocomplete({
            source: queueList
         });
    
        $("#input_qname_browseqs").autocomplete({
            source: function(request, response) {
              var term = request.term.toLowerCase();
              filteredList = $.grep(queueList, function(tag) {
                return tag.toLowerCase().indexOf(term) >= 0;
              });
              if (filteredList.length > 0) {
                filteredList.unshift("Select All");
              }
              response(filteredList);
            },
            select: function(event, ui) {
              if (ui.item.value === "Select All") {
                filteredList.shift();
                filteredList.forEach((queueName) => {
                    if (!queueNames.includes(queueName)) {
                        queueNames.push(queueName);
                    }
                })
                input_qname_browseqs.value = "";
                removeAllChildren(browseqsRootElement);
                browseqsRootElement.appendChild(createQueryTable());
                return false;
              } else {
                let queueName = ui.item.value;
                if (!queueNames.includes(queueName)) {
                    queueNames.push(queueName);
                }
                input_qname_browseqs.value = "";
                removeAllChildren(browseqsRootElement);
                browseqsRootElement.appendChild(createQueryTable());
                return false;
              }
            }
          });
    }

    // Test connection to queue manager
    btn_testqm.addEventListener("click", () => {
        if (isValid(input_qmname_testqm)) {
            testqm(input_qmname_testqm.value)
        };
    });

    // Reload queue list and clear query if another environment is selected
    select_environment.addEventListener("change", () => {
        getqlist(select_environment.value);
        clearQuery();
    })

    // Read queue list from file
    fileSelector.addEventListener("change", (event) => {
        let file = event.target.files[0];
        let fileReader = new FileReader();
        fileReader.addEventListener("load", (event) => {
            let fileContent = event.target.result;
            fileContent = fileContent.replace(/(\r\n|\n|\r)/gm, " ");
            fileContent = fileContent.split(" ");
            fileContent.forEach(element => {
                if (queueList.includes(element) && !queueNames.includes(element)) {
                    queueNames.push(element);
                }
            })
            removeAllChildren(browseqsRootElement);
                browseqsRootElement.appendChild(createQueryTable());
            fileSelector.value = null;
        })
        fileReader.readAsText(file);
    })

    // Save queue list to file
    btn_saveqlist.addEventListener("click", () => {
        if (queueNames.length != 0) {
            fileContentTemp = queueNames.join("\n");
            let fileContent = new Blob([fileContentTemp], {type: "text/plain"});
            let fileLink = document.createElement("a");
            fileLink.href = window.URL.createObjectURL(fileContent);
            fileLink.download = "queue_list.txt";
            fileLink.click();
            URL.revokeObjectURL(fileLink.href);
        }
    })

    // Browse queues
    btn_browseqs.addEventListener("click", () => {
        let searchParametersAndRelation = input_searchParametersAndRelation.checked;
        let getOnlyQueueDepth = input_getOnlyQueueDepth.checked;
        let formIsValid = true;
        let timeStampFrom = input_ts_from.value;
        let timeStampTo = input_ts_to.value;

        if (queueNames.length == 0) {
            input_qname_browseqs.classList.add("invalid");
            formIsValid = false;
        } else {
            input_qname_browseqs.classList.remove("invalid");
        }

        if (!getOnlyQueueDepth) {
            if (new Date(timeStampTo) <= new Date(timeStampFrom) && !(new Date(timeStampFrom) == 0 && new Date(timeStampTo) == 0)) {
            input_ts_from.classList.add("invalid");
            input_ts_to.classList.add("invalid");
            formIsValid = false;
        } else {
            input_ts_from.classList.remove("invalid");
            input_ts_to.classList.remove("invalid");
        }
        }

        if (formIsValid) {
            queryId = Date.now();
            let query = null;
            let environment = select_environment.value;
            let searchParameters = [];
            searchParameters.push(input_searchParameter1.value.trim());
            searchParameters.push(input_searchParameter2.value.trim());
            searchParameters.push(input_searchParameter3.value.trim());
            searchParameters.push(input_searchParameter4.value.trim());
            searchParameters = searchParameters.filter(searchParameter => searchParameter != "");
            if (searchParameters.length == 0) {
                searchParameters = null;
            }
            if (timeStampFrom.length == 0) {
                timeStampFrom = null;
            }
            if (timeStampTo.length == 0) {
                timeStampTo = null;
            }
            if (!getOnlyQueueDepth) {
                query = {
                    queryId: queryId,
                    environment: environment,
                    queueNames: queueNames,
                    searchParameters: searchParameters,
                    timeStampFrom: timeStampFrom,
                    timeStampTo: timeStampTo,
                    searchParametersAndRelation: searchParametersAndRelation,
                    getOnlyQueueDepth: getOnlyQueueDepth
                }
            } else if (getOnlyQueueDepth) {
                query = {
                    queryId: queryId,
                    environment: environment,
                    queueNames: queueNames,
                    searchParameters: null,
                    timeStampFrom: null,
                    timeStampTo: null,
                    searchParametersAndRelation: null,
                    getOnlyQueueDepth: getOnlyQueueDepth
                }
            }
            input_qname_browseqs.classList.remove("invalid");
            browseqs(query);
        }
    })

    input_getOnlyQueueDepth.addEventListener("click", () => {
        let fieldsToGreyOut = document.querySelectorAll(".greyable");
        fieldsToGreyOut.forEach(field => {
            if (field.disabled == false) {
                field.disabled = true;
            } else if (field.disabled == true) {
                field.disabled = false;
            }
        })
    })

    btn_clear_browseqs.addEventListener("click", clearQuery);

    // Clear query
    function clearQuery() {
        let fieldsToClear = document.querySelectorAll(".clearable");
        fieldsToClear.forEach(field => {
            field.value = null;
        })
        let fieldsInvalidated = document.querySelectorAll(".invalid");
        fieldsInvalidated.forEach(field => {
            field.classList.remove("invalid");
        })
        let fieldsGreyed = document.querySelectorAll(".greyable");
        fieldsGreyed.forEach(field => {
            if (field.disabled == true) {
                field.disabled = false;
            }
        })
        input_searchParametersAndRelation.checked = false;
        input_getOnlyQueueDepth.checked = false;
        queueNames.length = 0;
        removeAllChildren(browseqsRootElement);
        browseqsRootElement.appendChild(createQueryTable());
    }

    // Get environment list from backend
    function getenvlist() {
        const url = "/api/getenvlist";

        fetch(url, {
            method: "GET",
            headers:{
                "Accept": "application/json, text/plain, */*"
        }})
        .then(response => {
            switch (response.status) {
                case 200:
                    return response.json();
                case 429:
                    throw new Error("Too many requests, please try again later.");
                default:
                    throw new Error("An unexpected error has occcured.");
        }})
        .then(responseObject => {
            attachEnvironmentList(responseObject);
        })
        .catch(error => {
            attachEnvironmentList(defaultEnvList);
            console.log(error.message);
        })
    }

    // Get queue list from backend
    function getqlist(environment) {
        const url = "/api/getqlist";
        const urlParams = new URLSearchParams({
            "environment": environment
        })
        fetch(`${url}?${urlParams}`, {
            method: "GET",
            headers:{
                "Accept": "application/json, text/plain, */*"
        }})
        .then(response => {
            switch (response.status) {
                case 200:
                    return response.json();
                case 429:
                    throw new Error("Too many requests, please try again later.");
                default:
                    throw new Error("An unexpected error has occcured.");
        }})
        .then(responseObject => {
            attachAutoComplete(responseObject);
            queueList = responseObject;
        })
        .catch(error => {
            attachAutoComplete(defaultQueueList);
            queueList = defaultQueueList;
            console.log(error.message);
        })
    }

    // Test queue manager
    function testqm(queueManagerName) {
        const url = "/api/testqm";
        const urlParams = new URLSearchParams({
            "queuemanagername": queueManagerName
        })
        document.body.style.cursor = "wait";

        fetch(`${url}?${urlParams}`, {
            method: "GET",
            headers: {
                "Accept": "application/json, text/plain, */*"
            }
        })
        .then(response => {
            switch (response.status) {
                case 200:
                    return response.text();
                case 429:
                    throw new Error("Too many requests, please try again later.");
                default:
                    throw new Error("An unexpected error has occcured.");
            }
        }).then(responseText => {
            removeAllChildren(resultsRootElement);
            resultsRootElement.appendChild(document.createTextNode(JSON.stringify(responseText)))
            document.body.style.cursor = "default";
            }
        ).catch(error => {
            removeAllChildren(resultsRootElement);
            let errorMessage = document.createElement("div");
            errorMessage.classList.add("system-message");
            errorMessage.textContent = error.message;
            resultsRootElement.appendChild(errorMessage);
            document.body.style.cursor = "default";
        });
    }

    // Query queues
    function browseqs(query) {
        const url = "/api/browseqs";
        document.body.style.cursor = "wait";

        let intervalId = setInterval(() => {
            getquerystatus(query["queryId"]);
        }, 1000);
       
        fetch(url, {
            method: "POST",
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/json"
              },
            body: JSON.stringify(query)
        })
        .then (response => {
            switch (response.status) {
                case 200:
                    return response.json();
                case 204:
                    throw new Error("No matching messages found.");
                case 206:
                    throw new Error("Please check query statistics for queue depth(s).");
                case 422:
                    throw new Error("Invalid query parameters.");
                case 429:
                    throw new Error("Too many requests, please try again later.");
                default:
                    throw new Error("An unexpected error has occcured.");
            }
        })
        .then (response => {
            queryResults = response;
            removeAllChildren(resultsRootElement);
            resultsRootElement.appendChild(createResultsTable(response));
        })
        .catch(error => {
            removeAllChildren(resultsRootElement);
            let errorMessage = document.createElement("div");
            errorMessage.classList.add("system-message");
            errorMessage.textContent = error.message;
            resultsRootElement.appendChild(errorMessage);
        })
        .finally(() => {
            clearInterval(intervalId);
            queryStatusRootElement.textContent = `Query finished.`
            document.body.style.cursor = "default";
            getquerystats(queryId);
        })
    }

    // Get query statistics from backend
    function getquerystats(queryId) {
        const url = "/api/getquerystats";
        const urlParams = new URLSearchParams({
            "queryid": queryId
        });

        fetch(`${url}?${urlParams}`, {
            method: "GET",
            headers:{
                "Accept": "application/json, text/plain, */*"
            }
        })
        .then (response => {
            switch (response.status) {
                case 200:
                    return response.json();
                case 404:
                    throw new Error("Query statistics not available.");
                case 429:
                    throw new Error("Too many requests, please try again later.");
                default:
                    throw new Error("An unexpected error has occcured.");
            }
        })
        .then(responseObject => {
            removeAllChildren(queryStatsRootElement);
            queryStatsRootElement.appendChild(createQueryStatsTable(responseObject));
        })
        .catch(error => {
            removeAllChildren(queryStatsRootElement);
            let errorMessage = document.createElement("div");
            errorMessage.classList.add("system-message");
            errorMessage.textContent = error.message;
            queryStatsRootElement.appendChild(errorMessage);
        })
    }

    // Get query status from backend
    function getquerystatus(queryId) {
        const url = "/api/getquerystatus";
        const urlParams = new URLSearchParams({
            "queryid": queryId
        });

        fetch(`${url}?${urlParams}`, {
            method: "GET",
            headers:{
                "Accept": "application/json, text/plain, */*"
            }
        })
        .then (response => {
            switch (response.status) {
                case 200:
                    return response.json();
                case 404:
                    throw new Error("Query status not available.");
                case 429:
                    throw new Error("Too many requests, please try again later.");
                default:
                    throw new Error("An unexpected error has occcured.");
            }
        })
        .then(responseObject => {
            removeAllChildren(queryStatusRootElement);
            let statusMessage = document.createElement("div");
            statusMessage.classList.add("system-message");
            statusMessage.textContent = `Checking ${responseObject["queueName"]} (${responseObject["queueDepth"]}) on ${responseObject["queueManagerName"]}`;
            queryStatusRootElement.appendChild(statusMessage);
        })
        .catch(error => {
            removeAllChildren(queryStatusRootElement);
            let errorMessage = document.createElement("div");
            errorMessage.classList.add("system-message");
            errorMessage.textContent = error.message;
            queryStatusRootElement.appendChild(errorMessage);
        })
    }

    function attachEnvironmentList(environmentList) {
        environmentList.forEach(environment => {
            let option = document.createElement("option");
            option.value = environment;
            option.innerHTML = environment;
            select_environment.appendChild(option);
        })
        getqlist(environmentList[0]);
    }

    // Create query results table
    function createResultsTable(results) {

        // Clear download list
        downloadList.length = 0;

        // Main container
        let div_main = document.createElement("div");
        div_main.style.backgroundColor = "white";
        div_main.classList.add("system-message");

        // Main titlebar
        let div_main_titlebar = document.createElement("div");
        div_main_titlebar.style.backgroundColor = "#EEEEEE";
        div_main_titlebar.classList.add("div-mainbar", "border-right");

        // Main titlebar queryID
        let div_main_titlebar_queryid = document.createElement("div");
        div_main_titlebar_queryid.classList.add("border-right", "padded");
        div_main_titlebar_queryid.title = "Query ID";
        div_main_titlebar_queryid.textContent = `Query ID: ${queryId}`;
        div_main_titlebar.appendChild(div_main_titlebar_queryid);

        // Main titlebar buttons
        let div_main_titlebar_btns = document.createElement("div");
        div_main_titlebar_btns.classList.add("div-mainbar-buttons");

        // Expand/collapse all messages
        let expAllIsActive = false;
        let div_main_titlebar_btns_expall = document.createElement("div");
        div_main_titlebar_btns_expall.classList.add("div-mainbar-button");
        let expall = document.createElement("button");
        expall.innerHTML = "<img src='assets/expand_all.svg' class='icon'/>";
        expall.title = "Expand/collapse all messages";
        expall.classList.add("mainbar-button");
        expall.addEventListener(("click"), () => {
            let collapsableElements = document.querySelectorAll(".collapsable");
            let expandButtons = document.querySelectorAll(".expandbutton");
            // Expand all messages
            if (expAllIsActive == false) {
                collapsableElements.forEach((element) => {
                    if (element.classList.contains("collapsed")) {
                        element.classList.remove("collapsed");
                    }
                    expAllIsActive = true;
                })
                // Replace all button icons
                expandButtons.forEach((button) => {
                    button.innerHTML = "<img src='assets/collapse.svg' class='icon'/>";
                })

            // Collapse all messages
            } else if (expAllIsActive == true) {
                collapsableElements.forEach((element) => {
                    if (!element.classList.contains("collapsed")) {
                        element.classList.add("collapsed");
                    }
                    expAllIsActive = false;
                })
                // Replace all button icons
                expandButtons.forEach((button) => {
                    button.innerHTML = "<img src='assets/expand.svg' class='icon'/>";
                })
            }
        })
        div_main_titlebar_btns_expall.appendChild(expall);
        
        // Download selected messages button
        let div_main_titlebar_btns_dlsel = document.createElement("div");
        div_main_titlebar_btns_dlsel.classList.add("div-mainbar-button");
        let dlsel = document.createElement("button");
        dlsel.innerHTML = "<img src='assets/download_selected.svg' class='icon'/>";
        dlsel.title = "Download selected messages as ZIP"
        dlsel.id = "dlsel";
        dlsel.disabled = true;
        dlsel.classList.add("mainbar-button");
        dlsel.addEventListener("click", () => {
            downloadSelected(downloadList);
        })
        div_main_titlebar_btns_dlsel.appendChild(dlsel);

        // Select/unselect all messages
        let selectallClicked = false;
        let div_main_titlebar_btns_selectall = document.createElement("div");
        div_main_titlebar_btns_selectall.classList.add("div-mainbar-button");
        let selectall = document.createElement("button");
        selectall.innerHTML = "<img src='assets/unchecked.svg' class='icon'/>";
        selectall.classList.add("mainbar-button");
        selectall.addEventListener("click", () => {
            downloadList.length = 0;
            let checkBoxes = document.querySelectorAll(".checkbox");
            let dlsel = document.querySelector("#dlsel");
            if (selectallClicked == false) {
                // Select all
                checkBoxes.forEach((checkbox) => {
                    checkbox.innerHTML = "<img src='assets/checked.svg' class='icon'/>";
                    dlsel.disabled = false;
                    downloadList.push(checkbox.id);
                })
                selectall.innerHTML = "<img src='assets/checked.svg' class='icon'/>";
                selectallClicked = true;
            } else if (selectallClicked == true) {
                // Unselect all
                checkBoxes.forEach((checkbox) => {
                    checkbox.innerHTML = checkbox.innerHTML = "<img src='assets/unchecked.svg' class='icon'/>";
                    dlsel.disabled = true;
                    downloadList.length = 0;
                })
                selectall.innerHTML = "<img src='assets/unchecked.svg' class='icon'/>";
                selectallClicked = false;
            }
        });
        selectall.title = "Select/unselect all messages";
        div_main_titlebar_btns_selectall.appendChild(selectall);

        // Add buttons to button bar
        div_main_titlebar_btns.appendChild(div_main_titlebar_btns_expall);
        div_main_titlebar_btns.appendChild(div_main_titlebar_btns_dlsel);
        div_main_titlebar_btns.appendChild(div_main_titlebar_btns_selectall);

        // Add button bar to main title bar
        div_main_titlebar.appendChild(div_main_titlebar_btns);
        
        // Add main titlebar to main node
        div_main.appendChild(div_main_titlebar);

        for ([key, message] of Object.entries(results)) {

            let messageKey = key;
            let formattedPayload = formatPayload(message["messagePayload"]);
            
            // Message node
            let div_msg = document.createElement("div");

            // Title bar
            let div_msg_titlebar = document.createElement("div");
            div_msg_titlebar.style.display = "flex";
            div_msg_titlebar.style.flexDirection = "row";
            div_msg_titlebar.style.justifyContent = "space-between";
            div_msg_titlebar.style.backgroundColor = "#EEEEEE";
            div_msg_titlebar.classList.add("border-top", "border-bottom");

            // Message properties
            let div_msg_props = document.createElement("div");
            div_msg_props.style.display = "flex";
            div_msg_props.style.flexDirection = "row";
            
            // Message number
            let div_msg_props_id = document.createElement("div");
            div_msg_props_id.classList.add("border-right", "padded");
            div_msg_props_id.title = "Message number";
            div_msg_props_id.textContent = `${parseInt(key) + 1}/${Object.keys(results).length}`;

            // Timestamp
            let div_msg_props_ts = document.createElement("div");
            div_msg_props_ts.style.whiteSpace = "nowrap";
            div_msg_props_ts.classList.add("border-right", "padded");
            div_msg_props_ts.title = "Put timestamp";
            let putTimeStamp = message["putTimeStamp"];
            div_msg_props_ts.textContent = putTimeStamp.replace("T", " ");

            // Source queue
            let div_msg_props_q = document.createElement("div");
            div_msg_props_q.classList.add("border-right", "padded");
            div_msg_props_q.title = "Source queue";
            div_msg_props_q.textContent = message["sourceQueue"];

            // Source queuemanager
            let div_msg_props_qm = document.createElement("div");
            div_msg_props_qm.classList.add("border-right", "padded");
            div_msg_props_qm.title = "Source queue manager";
            div_msg_props_qm.textContent = message["sourceQueueManager"];

            // MQID
            let div_msg_props_mqid = document.createElement("div");
            div_msg_props_mqid.classList.add("border-right", "padded");
            div_msg_props_mqid.title = "Message ID";
            div_msg_props_mqid.textContent = message["messageId"];


            // Message button bar
            let div_msg_buttons = document.createElement("div");
            div_msg_buttons.classList.add("div-msg-buttons", "border-right");

            // Expand button
            let div_msg_btns_expbtn = document.createElement("div");
            div_msg_btns_expbtn.classList.add("div-msg-button");
            let expandbutton = document.createElement("button");
            expandbutton.classList.add("msg-button", "expandbutton");
            expandbutton.title = "Expand message";
            expandbutton.innerHTML = "<img src='assets/expand.svg' class='icon'/>";
            expandbutton.addEventListener("click", () => {
                if (div_msg_body.classList.contains("collapsed")) {
                    div_msg_body.classList.remove("collapsed");
                    expandbutton.innerHTML = "<img src='assets/collapse.svg' class='icon'/>";
                    expandbutton.title = "Collapse message";
                } else if (!div_msg_body.classList.contains("collapsed")) {
                    div_msg_body.classList.add("collapsed");
                    expandbutton.innerHTML = "<img src='assets/expand.svg' class='icon'/>";
                    expandbutton.title = "Expand message";
                }
            })
            div_msg_btns_expbtn.appendChild(expandbutton);

            // Copy button
            let div_msg_btns_copybtn = document.createElement("div");
            div_msg_btns_copybtn.classList.add("div-msg-button");
            let copybtn = document.createElement("button");
            copybtn.classList.add("msg-button");
            copybtn.innerHTML = "<img src='assets/copy.svg' class='icon'/>";
            copybtn.title = "Copy message text to clipboard";
            copybtn.addEventListener("click", () => {
                navigator.clipboard.writeText(formattedPayload);
            });
            div_msg_btns_copybtn.appendChild(copybtn);

            // Download button
            let div_msg_btns_dlbtn = document.createElement("div");
            div_msg_btns_dlbtn.classList.add("div-msg-button");
            let downloadbtn = document.createElement("button");
            downloadbtn.classList.add("msg-button");
            downloadbtn.innerHTML = "<img src='assets/download.svg' class='icon'/>";
            downloadbtn.title = "Download message";
            let fileName = assembleFileName(message);
            let fileDownloaded = false;
            downloadbtn.addEventListener("click", () => {
                let fileContent = new Blob([formattedPayload], {type: "text/plain"});
                if (!fileDownloaded) {
                    if (formattedPayload.charAt(0) == "<") {
                        fileName += ".xml";
                    } else if (formattedPayload.charAt(0) == "{") {
                        fileName += ".json";
                    } else {
                        fileName += ".txt";
                    }
                }
                let fileLink = document.createElement('a');
                fileLink.href = window.URL.createObjectURL(fileContent);
                fileLink.download = fileName;
                fileDownloaded = true;
                fileLink.click();
            })
            div_msg_btns_dlbtn.appendChild(downloadbtn);

            // Select button
            let div_msg_btns_selectbtn = document.createElement("div");
            div_msg_btns_selectbtn.classList.add("div-msg-button");
            let checkbox = document.createElement("button");
            checkbox.id = messageKey;
            checkbox.classList.add("msg-button");
            checkbox.classList.add("checkbox");
            checkbox.innerHTML = "<img src='assets/unchecked.svg' class='icon'/>";
            checkbox.title = "Select/unselect message";
            checkbox.addEventListener("click", () => {
                let dlsel = document.querySelector("#dlsel");
                if (!downloadList.includes(checkbox.id)) {
                    checkbox.innerHTML = "<img src='assets/checked.svg' class='icon'/>";
                    downloadList.push(checkbox.id);
                    dlsel.disabled = false;
                } else if (downloadList.includes(checkbox.id)) {
                    checkbox.innerHTML = "<img src='assets/unchecked.svg' class='icon'/>";
                    downloadList = downloadList.filter(id => id != checkbox.id);
                    if (downloadList.length == 0) {
                        dlsel.disabled = true;
                    }
                }
            });
            div_msg_btns_selectbtn.appendChild(checkbox);

            // Assemble message properties
            div_msg_props.appendChild(div_msg_props_id);
            div_msg_props.appendChild(div_msg_props_ts);
            div_msg_props.appendChild(div_msg_props_q);
            div_msg_props.appendChild(div_msg_props_qm);
            div_msg_props.appendChild(div_msg_props_mqid);

            // Add message properties to title bar
            div_msg_titlebar.appendChild(div_msg_props);

            // Add message buttons to button bar
            div_msg_buttons.appendChild(div_msg_btns_expbtn);
            div_msg_buttons.appendChild(div_msg_btns_copybtn);
            div_msg_buttons.appendChild(div_msg_btns_dlbtn);
            div_msg_buttons.appendChild(div_msg_btns_selectbtn);
            div_msg_titlebar.appendChild(div_msg_buttons);
            
            // Add button bar to title bar
            div_msg.appendChild(div_msg_titlebar);

            // Message header
            if (message["messageHeader"] != null) {
                let div_msg_head = document.createElement("div");
                div_msg_head.classList.add("border-bottom-header");
                div_msg_head.textContent = message["messageHeader"];
                div_msg_head.style.height = "100px";
                div_msg_head.style.overflowWrap = "break-word";
                div_msg_head.style.overflow = "auto";
                div_msg_head.style.padding = "5px";
                div_msg_head.title = "Message header";
                div_msg.appendChild(div_msg_head);
            }

            // Message payload
            let div_msg_body = document.createElement("div");
            div_msg_body.textContent = formattedPayload;
            div_msg_body.style.whiteSpace = "pre-wrap";
            div_msg_body.style.overflowWrap = "break-word";
            div_msg_body.style.overflow = "auto";
            div_msg_body.style.padding = "5px";
            div_msg_body.title = "Message body";
            div_msg_body.classList.add("collapsable", "collapsed");
            div_msg.appendChild(div_msg_body);
            div_main.appendChild(div_msg);
        }
        return div_main;
    }

    // Create query statistics table
    function createQueryStatsTable(queryStats) {
        let queryStatsTable = document.createElement("div");
        
        queryStatsTable.classList.add("query-stats-table");

        // Table head
        let queryStatsTableHead = document.createElement("div");
        queryStatsTableHead.classList.add("query-stats-table-head", "border-bottom");

        // Table head buttons
        let div_qstat_copybtn = document.createElement("div");
        div_qstat_copybtn.classList.add("div-qstat-button");
        let copybtn = document.createElement("button");
        copybtn.classList.add("qstat-button");
        copybtn.innerHTML = "<img src='assets/copy.svg' class='icon'/>";
        copybtn.title = "Copy query log to clipboard";
        copybtn.addEventListener("click", () => {
            navigator.clipboard.writeText(contentString);
        });
        div_qstat_copybtn.appendChild(copybtn);
        queryStatsTableHead.appendChild(div_qstat_copybtn);

        // Table content
        let queryStatsTableContent = document.createElement("div");
        queryStatsTableContent.classList.add("query-stats-table-content");

        let queryStatsEntries = queryStats["queryStatsEntries"];
        let searchParameters = queryStats["searchParameters"];

        let contentString = "";

        // Query properties
        contentString +=
        `---\r\n` +
        `Application version: ${applicationName} ${applicationVersion}\r\n` +
        `Query ID: ${queryStats["queryId"]}\r\n` +
        `Environment: ${queryStats["environment"]}\r\n` +
        `Query started: ${queryStats["queryStartTime"]}\r\n` +
        `Query finished: ${queryStats["queryEndTime"]}\r\n`;

        // Search parameters
        if (searchParameters == null) {
            searchParameters = "--"
        }
        if (queryStats["timeStampFrom"] == null) {
            queryStats["timeStampFrom"] = "--"
        }
        if (queryStats["timeStampTo"] == null) {
            queryStats["timeStampTo"] = "--"
        }
        contentString +=
        `---\r\n` +
        `Search parameters: ${searchParameters}\r\n` +
        `Timestamp from: ${queryStats["timeStampFrom"]}\r\n` +
        `Timestamp to: ${queryStats["timeStampTo"]}\r\n` +
        `AND relation between search parameters: ${queryStats["searchParametersAndRelation"]}\r\n` +
        `Get only queue depth: ${queryStats["getOnlyQueueDepth"]}\r\n`;

        // Queues
        contentString +=
        `---\r\n` +
        `Queried queues (total / matching messages):\r\n`;
        queryStatsEntries.forEach((entry) => {
            contentString +=
            `${entry["queueName"]} (${entry["queueManagerName"]}): ${entry["queueDepth"]} / ${entry["matchingMessagesCount"]}\r\n`;
        })

        contentString += `---`;

        queryStatsTableContent.textContent = contentString;

        queryStatsTable.appendChild(queryStatsTableHead);
        queryStatsTable.appendChild(queryStatsTableContent);

        return queryStatsTable;
    }

    // Create query table
    function createQueryTable() {
        let queryTable = document.createElement("div");
        queryTable.id = "query_table";
        queryTable.classList.add("query-table");
        if (queueNames.length == 0) {
            queryTable.style.backgroundColor = "white";
            queryTable.style.border = "1px solid lightgrey";
            queryTable.style.color = "grey";
            queryTable.textContent = "Queue list is empty, please select queues.";
            btn_saveqlist.disabled = true;
        } else {
            btn_saveqlist.disabled = false;
            let rowCounter = 0;
            queueNames.forEach(queueName => {
                let queryTableRow = document.createElement("div");
                queryTableRow.id = `qtr_${rowCounter}`;
                queryTableRow.classList.add("query-table-row");
                let queueNameCell = document.createElement("div");
                queueNameCell.id = `qnc_${rowCounter}`;
                queueNameCell.textContent = queueName;
                queryTableRow.appendChild(queueNameCell);
                let delButtonCell = document.createElement("div");
                delButtonCell.classList.add("div-qtable-button");
                let delButton = document.createElement("button");
                delButton.classList.add("qtable-button");
                delButton.innerHTML = `<img src='assets/delete.svg' class='icon'/ id=${rowCounter}>`;
                delButton.title = "Remove queue from the list"
                delButton.addEventListener("click", (event) => {
                    let queueNameToDelete = document.querySelector(`#qnc_${event.target.id}`).textContent;
                    queueNames = queueNames.filter(queueName => queueName != queueNameToDelete);
                    let rowToDelete = `qtr_${event.target.id}`;
                    document.getElementById(rowToDelete).remove();
                    if (queueNames.length == 0) {
                        removeAllChildren(document.querySelector("#query_table"));
                        queryTable.style.border = "1px solid lightgrey";
                        queryTable.style.color = "grey";
                        queryTable.textContent = "Queue list is empty, please select queues.";
                        btn_saveqlist.disabled = true;
                    }
            })
            delButtonCell.appendChild(delButton);
            queryTableRow.appendChild(delButtonCell);
            queryTable.appendChild(queryTableRow);
            rowCounter ++;
            })
        }
        return queryTable;
    }

    // Pretty print messages
    function formatPayload(messagePayload) {

        // If JSON
        if (messagePayload.charAt(0) == "{") {
            let parsedJson = JSON.parse(messagePayload);
            return JSON.stringify(parsedJson, null, 2);
        }

        // If XML
        if (messagePayload.charAt(0) == "<") {
            let xmlDoc = new DOMParser().parseFromString(messagePayload, 'application/xml');
            let xsltDoc = new DOMParser().parseFromString([
                '<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform">',
                '  <xsl:strip-space elements="*"/>',
                '  <xsl:template match="para[content-style][not(text())]">',
                '    <xsl:value-of select="normalize-space(.)"/>',
                '  </xsl:template>',
                '  <xsl:template match="node()|@*">',
                '    <xsl:copy><xsl:apply-templates select="node()|@*"/></xsl:copy>',
                '  </xsl:template>',
                '  <xsl:output indent="yes"/>',
                '</xsl:stylesheet>',
            ].join('\n'), 'application/xml');
    
            let xsltProcessor = new XSLTProcessor();    
            xsltProcessor.importStylesheet(xsltDoc);
            let resultDoc = xsltProcessor.transformToDocument(xmlDoc);
            let resultXML = new XMLSerializer().serializeToString(resultDoc);
            return resultXML;
        }
    
        // If not recognized
        return(messagePayload);
    }

    // Assemble fine name for download
    function assembleFileName(message) {
        let queueName = message["sourceQueue"];
        let tsDate = message["putTimeStamp"].substring(0, 10).replaceAll("-", "");
        let tsTime = message["putTimeStamp"].substring(11,22).replaceAll(":", "");
        tsTime = tsTime.replaceAll(".", "");
        let msgId = message["messageId"].substring(message["messageId"].length - 8);
        let fileName = `${queueName}_${tsDate}_${tsTime}_${msgId}`;
        return fileName;
    }


    // Download file
    async function downloadSelected(downloadList) {
        const zip = new JSZip();
        downloadList.forEach((messageKey) => {
            let message = queryResults[messageKey];
            let formattedPayload = formatPayload(message["messagePayload"]);
            let fileContent = new Blob([formattedPayload], {type: "text/plain"});
            let fileName = assembleFileName(message);
            if (formattedPayload.charAt(0) == "<") {
                fileName += ".xml";
            } else if (formattedPayload.charAt(0) == "{") {
                fileName += ".json";
            } else {
                fileName += ".txt";
            }
            zip.file(fileName, fileContent);
        })
        let zipFile = await zip.generateAsync({
            type: "blob",
            streamFiles: true
        })
        let fileLink = document.createElement('a');
        fileLink.href = window.URL.createObjectURL(zipFile);
        fileLink.download = zipFileName;
        fileLink.click();
    }

    // General function to clear HTML nodes
    function removeAllChildren(node) {
        while (node.firstChild) {
            node.removeChild(node.lastChild);
        }
    }

    // General function to check HTML entry field validity
    function isValid(fieldName) {
        if (fieldName.value.length != 0) {
            fieldName.classList.remove("invalid");
            return true;
        } else {
            fieldName.classList.add("invalid");
            return false;
        }
    }
}

document.addEventListener("DOMContentLoaded", main);