/**
 *
 * Checks if the length of the password is between 8 and 16 chars
 *
 * @returns {boolean} true if length is ok
 */
function isPswLengthSafe(psw) {

    if (psw.length < 8 || psw.length > 16) {
        return false;
    } else {
        return true;
    }
}

/**
 *
 * Checks if the given password has at least
 *
 * @returns {boolean} true if chars are ok
 */
function isPswCharSafe(psw){

    var lowerCases = /[a-z]/g;
    var upperCases = /[A-Z]/g;
    if (!psw.match(upperCases) || !psw.match(lowerCases)){
        return false;
    } else {
        return true;
    }
}

/**
 *
 * Checks if the password field given contains a number
 *
 * @returns {boolean} true if has numbers
 */
function hasNumbers(psw){

    var numbers = /[0-9]/g;
    if (!psw.match(numbers)) {
        return false
    } else {
        return true;
    }
}

/**
 *
 * Checks if the size of the file is minor that 5MB
 *
 * @returns {boolean} true if it is smaller than 5MB
 */
function isFileSizeValid(){
    const fileInput =
        document.getElementById('file');

    // 5 MB
    if (fileInput.files[0].size/1024 > 5120){
        return false //File is too big
    }
    else {
        return true //Proceed with validation
    }
}

/**
 *
 * Checks if the document file is valid (allowed: pdf)
 *
 * @returns {boolean} true if it is valid
 */
function isDocumentFileValid() {
    const fileInput =
        document.getElementById('file');

    const filePath = fileInput.value;
    // Allowed file type
    const allowedExtensions = /(\.pdf)$/i;

    if (!allowedExtensions.exec(filePath)) {
        return false       //Show error message
    }
    else {
        return true        //Proceed with validation
    }
}

/**
 *
 * Checks if the image file is valid (allowed: jpg, jpeg, png)
 *
 * @returns {boolean} true if it is valid
 */
function isImageFileValid() {
    const fileInput =
        document.getElementById('file');

    const filePath = fileInput.value;
    let lowerFilePath = filePath.toLowerCase();

    // Allowed file type
    const allowedExtensions =
        /(\.jpg|\.jpeg|\.png)$/i;

    if (!allowedExtensions.exec(lowerFilePath)) {
        return false       //Show error message
    }
    else {
        return true        //Proceed with validation
    }
}

/**
 *
 * Checks if the telephone length is valid
 *
 * @param telephone the telephone passed in
 * @returns {boolean} true if it is valid
 */
function isPhoneLengthValid(telephone){
    if (telephone.length !== 10){
        return false;
    } else {
        return true;
    }
}

/**
 *
 * Checks if password and confirm password are the same
 *
 * @param password the password input from the form
 * @param confirmedPassword the confirmed password input from the form
 * @returns {boolean} true if they are the same
 */
function isConfirmedPswMatching (password, confirmedPassword){
    if (password !== confirmedPassword) {
        return false;
    } else {
        return true;
    }
}
