//TODO: fix variable declarations inside functions: var/const/let ?
/**
 *
 * Checks if the length of the password is between 8 and 16 chars
 *
 * @returns {boolean} true if length is ok
 */
export function isPswLengthSafe(psw) {
    //var psw = document.getElementById('password');

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
export function isPswCharSafe(psw){

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
export function hasNumbers(psw){

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
export function isFileSizeValid(fileInput){

    if (fileInput.files[0].size/1024 > 5120){ //5MB
        return false;
    }
    else {
        return true;
    }
}

/**
 *
 * Checks if the file type is valid (allowed: jpg, jpeg, png)
 *
 * @returns {boolean} true if it is valid
 */
export function isFileTypeValid(fileInput) {

    const filePath = fileInput.value;
    // Allowed file type
    const allowedExtensions =
        /(\.jpg|\.jpeg|\.png)$/i;

    if (!allowedExtensions.exec(filePath)) {
        return false;
    }
    else {
        return true;
    }
}
