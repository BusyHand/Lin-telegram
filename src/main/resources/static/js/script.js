const searchInputD2 = document.getElementById('searchBoxD2');
const suggestionBoxD2 = document.getElementById('suggestionsD2');
const searchFormD2 = document.getElementById('searchFormD2');
const createButton = document.getElementById('createButton');
const createEntityModal = document.getElementById('createEntityModal');
const closeButton = document.querySelector('.close-button');
const createEntityForm = document.getElementById('createEntityForm');

// Debounce function for delaying requests
searchInputD2.addEventListener('input', debounce(fetchSuggestionsD2, 300));

function debounce(func, delay) {
    let timeout;
    return function (...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), delay);
    };
}

function fetchSuggestionsD2() {
    const query = searchInputD2.value.trim();
    if (query.length <= 0) {
        suggestionBoxD2.innerHTML = '';
        return;
    }

    // Get the selected radio button value
    const selectedType = document.querySelector('input[name="type"]:checked');
    if (!selectedType) {
        console.error('No radio button is selected.');
        return; // Exit if no radio button is selected
    }

    const endpoint = selectedType.value; // Use the value of the selected radio button
    const url = `/api/${endpoint}/suggestions?query=${encodeURIComponent(query)}`;
    console.log(`Fetching suggestions from: ${url}`);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            displaySuggestionsD2(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            suggestionBoxD2.innerHTML = '';
        });
}

function displaySuggestionsD2(suggestions) {
    suggestionBoxD2.innerHTML = ''; // Clear previous suggestions
    if (suggestions.length === 0) return; // No suggestions found

    suggestions.forEach(suggestion => {
        const suggestionItem = document.createElement('div');
        suggestionItem.textContent = suggestion;
        suggestionItem.classList.add('suggestion-item');
        suggestionItem.onclick = () => selectSuggestionD2(suggestion);
        suggestionBoxD2.appendChild(suggestionItem);
    });
}

function selectSuggestionD2(suggestion) {
    searchInputD2.value = suggestion;
    suggestionBoxD2.innerHTML = ''; // Clear suggestions
}

// Allow form submission
searchFormD2.addEventListener('submit', (event) => {
    console.log('Form submitted with query:', searchInputD2.value); // Debug log
});

createButton.addEventListener('click', () => {
    createEntityModal.style.display = "block"; // Show the modal
    const termRadioButton = document.querySelector('input[value="terms"]'); // Target the Term radio button
    setActiveTile(termRadioButton.closest('.tile')); // Set active class for the Term tile
});

// Close modal if the close button is clicked
closeButton.onclick = function () {
    createEntityModal.style.display = "none"; // Hide the modal
    const termRadioButton = document.querySelector('input[value="terms"]'); // Re-target the Term radio button
    setActiveTile(termRadioButton.closest('.tile')); // Set active class for the Term tile again
};

// Handle Create Entity Form Submission
createEntityForm.addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent the default form submission

    const entityName = document.getElementById('entityName').value;
    const entityDescription = document.getElementById('entityDescription').value;

    const newEntityData = {
        name: entityName,
        description: entityDescription
    };

    // Post data to create a new entity
    fetch(`/api/entities`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // Specify the content type
        },
        body: JSON.stringify(newEntityData), // Convert the object to JSON string
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to create entity');
            createEntityModal.style.display = "none"; // Hide modal
            location.reload(); // Reload the page or refresh the UI as necessary
        })
        .catch(error => console.error('Error creating entity:', error)); // Log any errors
});

// Main Form Logic
// Function to manage active classes for tiles
function setActiveTile(tile) {
    const mainTiles = document.querySelectorAll('.main-tile'); // Select all main tiles
    mainTiles.forEach(t => {
        t.classList.remove('active'); // Remove active class from all tiles
        t.querySelector('input[type="radio"]').checked = false; // Uncheck all radios
    });

    tile.classList.add('active'); // Add active class to the clicked tile
    tile.querySelector('input[type="radio"]').checked = true; // Check the specific radio button
}

// Main Form Logic on DOM Content Loaded
document.addEventListener("DOMContentLoaded", function () {
    const mainTiles = document.querySelectorAll('.main-tile'); // Select all main tiles
    console.log("Main Tiles:", mainTiles); // Log the mainTiles NodeList

    if (mainTiles.length === 0) {
        console.error("No tiles found with class 'main-tile'");
        return; // Exit if no tiles are found
    }

    // Set the first radio button tile as active initially for the main form
    mainTiles[0].classList.add('active');
    console.log("First tile set as active:", mainTiles[0]); // Log first tile that is set as active

    // Handle click events for main tiles
    mainTiles.forEach(tile => {
        tile.addEventListener('click', function () {
            setActiveTile(this); // Call function to set the clicked tile as active
        });
    });

    // Other existing code...
});

createEntityForm.addEventListener('submit', (event) => {
    event.preventDefault(); // Prevent the default form submission

    const entityName = document.getElementById('entityName').value;
    const entityDescription = document.getElementById('entityDescription').value;

    const newEntityData = {
        name: entityName,
        description: entityDescription
    };

    const selectedEntityType = document.querySelector('input[name="entityType"]:checked').value;

    // Post data to create a new entity using the selected entity type
    fetch(`/api/${selectedEntityType}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newEntityData),
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to create entity');
            createEntityModal.style.display = "none"; // Hide modal
            location.reload(); // Reload the page or refresh the UI as necessary
        })
        .catch(error => console.error('Error creating entity:', error));
});


