

const addTaskModal = document.querySelector('.add-task-modal-container');
const openAddTaskModal = document.querySelector('#open-add-task-modal');
const closeAddTaskModal = document.querySelector('#close-add-task-modal')




openAddTaskModal.addEventListener('click', () => {

    window.location.href='/add-task.html'
    addTaskModal.classList.remove('hidden');
    console.log('Should show modal')
})

closeAddTaskModal.addEventListener('click', () => {
    addTaskModal.classList.add('hidden');
    windowhistory.pushState({}, '', '')
    console.log('Should hide modal');
})





