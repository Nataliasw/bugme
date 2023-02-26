function setState() {
    const issueId = document.getElementById("issue-id");
    if (!issueId) {
        return;
    }

    const newState = document.getElementById("state").value;
    const body = {
        state: newState
    };

    axios.patch(`/issues/state/${issueId.value}`, body).then(response => {
        console.log(response);
    }).catch(error => {
        console.log(error);
    })
}