document.addEventListener('DOMContentLoaded', () => {
    const suggestionItems = document.querySelectorAll('.result-item');
    const infoModal = document.getElementById('infoModal');
    const modalContent = document.getElementById('modalContent');
    const closeButton = document.querySelector('.close-button');

    suggestionItems.forEach(item => {
        item.addEventListener('click', () => {
            const itemId = item.getAttribute('data-id');

            // Attempt to retrieve the selected type directly from the radio buttons
            let selectedRadio = document.querySelector('input[name="type"]:checked');

            // Check if the selected radio does not exist
            if (!selectedRadio) {
                console.warn('No radio button selected, attempting to retrieve from localStorage.');
                // Fallback to local storage if no radio button is checked
                const storedType = localStorage.getItem('selectedSearchType');
                if (storedType) {
                    selectedRadio = { value: storedType }; // Create a mock object with the stored value
                    console.log(`Using stored radio button type: ${storedType}`);
                } else {
                    console.error('No radio button type has been determined, and no stored value exists!');
                    return; // Exit if no radio button is checked or no stored value available
                }
            }

            const selectedType = selectedRadio.value; // Safe to access the value now
            const url = `/api/${selectedType}/${itemId}`;

            console.log(`Fetching details from: ${url}`); // Debug log

            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to fetch details: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    // Populate the modal with the fetched data
                    modalContent.innerHTML = `
                        <h3>${data.title || data.name}</h3>
                        <p>${data.description || 'Нет описания.'}</p>
                        ${data.tools ? '<h4>Инструменты:</h4><ul>' + data.tools.map(tool => `<li>${tool.name || tool}</li>`).join('') + '</ul>' : ''}
                        ${data.cases ? '<h4>Связанные дела:</h4><ul>' + data.cases.map(bCase => `<li>${bCase.title || bCase}</li>`).join('') + '</ul>' : ''}
                    `;
                    infoModal.style.display = "block"; // Show modal
                })
                .catch(error => {
                    console.error('Error fetching details:', error);
                });
        });
    });

    closeButton.onclick = function() {
        infoModal.style.display = "none"; // Close the modal
    };

    window.onclick = function(event) {
        if (event.target === infoModal) {
            infoModal.style.display = "none"; // Close the modal on outside click
        }
    };
});
