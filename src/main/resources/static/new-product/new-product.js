import fetchData from "../fetchData/fetchData.js";
import fetchDataStatus from "../fetchData/fetchDataStatus.js";
import { getCategoriesData } from "../fetchData/getCategoriesData.js";
import { getSalesFormatData } from "../fetchData/getSalesFormatData.js";
import { productDetailView } from "../product-detail/productDetailView.js";

document.addEventListener("DOMContentLoaded", function () {
    createListCountries();
    renderSalesFormats();
    renderCategories()
})


async function renderSalesFormats() {
    // Obtener el array desde la API o función
    let salesFormatArray = await getSalesFormatData("GET", 0, {}, {});

    const selectElement = document.getElementById("price-format-new-product");
    if (!selectElement) return;

    // Limpiar opciones previas excepto la primera (placeholder)
    while (selectElement.options.length > 1) {
        selectElement.remove(1);
    }

    // Agregar opciones dinámicamente
    salesFormatArray.forEach(format => {
        const option = document.createElement("option");
        option.value = format.id; // O puedes usar format.id si prefieres
        option.textContent = format.name;
        option.dataset.name = format.name;
        selectElement.appendChild(option);
    });
}

async function renderCategories() {
    // Obtener el array desde la API o función
    let categoriesArray = await getCategoriesData("GET", 0, {}, {});

    const selectElement = document.getElementById("category-new-product");
    if (!selectElement) return;

    // Limpiar opciones previas excepto la primera (placeholder)
    while (selectElement.options.length > 1) {
        selectElement.remove(1);
    }

    const option = document.createElement("option");
    option.value = "";
    option.textContent = "Seleccione una categoría";
    option.dataset.name = ""
    selectElement.appendChild(option);

    // Agregar opciones dinámicamente
    categoriesArray.forEach(format => {
        const option = document.createElement("option");
        option.value = format.id; // O puedes usar format.id si prefieres
        option.textContent = format.name;
        option.dataset.name = format.name;
        selectElement.appendChild(option);
    });
}

// Variable global para almacenar los países
let countries = [];

async function getCountriesData() {
    try {
        const data = await fetchData('/countries', 'GET', {}, {});
        return data;
    } catch (error) {
        console.error(error);
        throw new Error("Error de conexión con la API");
    }
}

async function createListCountries() {
    countries = await getCountriesData();

    const select = document.getElementById("country-new-product");
    select.innerHTML = `<option selected value="">Selecciona un país</option>`;

    countries.forEach(country => {
        const option = document.createElement("option");
        option.value = country.id;
        option.textContent = country.name;
        select.appendChild(option);
    });

    // Si hay algún país seleccionado por defecto, renderizamos sus estados
    if (select.selectedIndex > 0) {
        renderStates(select);
    }
}

window.renderStates = (selectCountry) => {
    const selectedIndex = selectCountry.selectedIndex - 1; // -1 por el placeholder
    const selectEstados = document.getElementById("state-new-product");

    // Reiniciamos el select de estados
    selectEstados.innerHTML = `<option selected value="">Selecciona un estado</option>`;

    if (selectedIndex < 0) {
        selectEstados.disabled = true;
        return;
    }

    const country = countries[selectedIndex];

    if (country.states && country.states.length > 0) {
        selectEstados.disabled = false;

        // Ordenamos estados A-Z
        const sortedStates = country.states.sort((a, b) => a.name.localeCompare(b.name));

        sortedStates.forEach(state => {
            const option = document.createElement("option");
            option.value = state.id;
            option.textContent = state.name;
            selectEstados.appendChild(option);
        });
    } else {
        selectEstados.disabled = true;
    }
};

// Llamada inicial para llenar los países
createListCountries();

window.generatePreview = () => {
    validateFormFields({ showAlert: false });

    const data = getFormData();
    const inputImagenes = document.getElementById("image-new-product");
    const preview = document.getElementById("preview-new-product");

    if (inputImagenes && inputImagenes.files.length > 0) {
        const file = inputImagenes.files[0];
        const reader = new FileReader();

        reader.onload = function (e) {
            data.picture = e.target.result;

            preview.innerHTML = `
                <h2 style="text-align: center; margin-bottom: 1rem;">Información del producto</h2>
                ${productDetailView(data, "preview")}
            `;

            preview.classList.remove("d-none");
            preview.style.display = "block";
            preview.scrollIntoView({ behavior: "smooth", block: "start" });
        };

        reader.readAsDataURL(file);
    } else {
        // Si no hay imagen, solo muestra el resto
        preview.innerHTML = `
            <h2 style="text-align: center; margin-bottom: 1rem;">Información del producto</h2>
            ${productDetailView(data)}
        `;

        preview.classList.remove("d-none");
        preview.style.display = "block";
        preview.scrollIntoView({ behavior: "smooth", block: "start" });
    }
};



window.saveProduct = async () => {
    const esValido = validateFormFields({ showAlert: true });
    if (!esValido) return;

    const formData = await getFormData();
    const body = {
        productName: formData.productName,
        sku: formData.sku,
        description: formData.description,
        price: parseFloat(formData.price) || 0,
        quantityStock: parseInt(formData.quantityStock) || 0,
        weight: parseFloat(formData.weight) || 0,
        length: parseFloat(formData.length) || 0,
        width: parseFloat(formData.width) || 0,
        height: parseFloat(formData.height) || 0,
        status: formData.status ?? true,
        lowStockThreshold: formData.lowStockThreshold ?? 10,
        allowBackorders: formData.allowBackorders ?? false,
        productSalesFormatId: formData.productSalesFormatId ? { id: parseInt(formData.productSalesFormatId) } : null,
        productCountryId: formData.productCountryId ? { id: parseInt(formData.productCountryId) } : null,
        productStateId: formData.productStateId ? { id: parseInt(formData.productStateId) } : null,
        productCategoryId: formData.productCategoryId ? { id: parseInt(formData.productCategoryId) } : 1,
        image: formData.image ?? null, // base64 si existe
        picture: formData.picture ?? null,
        discountType: formData.discountType ?? null,
        discountValue: formData.discountValue ? parseFloat(formData.discountValue) : null
    };

    try {
        // Llamada al backend
        const response = await fetchDataStatus("/product", "POST", {}, body);
        // Si tu fetchData retorna un objeto con status
        if (response.status === 200 || response.status === 201) {
            Swal.fire({
                icon: 'success',
                title: 'Producto guardado',
                text: 'El producto ha sido agregado correctamente.',
                timer: 2000, // 200 milisegundos
                showConfirmButton: false,
                timerProgressBar: true
            }).then(() => {
                // Ir al inicio de la página
                window.scrollTo({ top: 0, behavior: 'smooth' });

                // Recargar la página
                window.location.reload();
            });

        } else if (response.status === 409) {
            // Conflicto, por ejemplo SKU repetido
            Swal.fire({
                icon: 'error',
                title: 'Conflicto',
                text: 'El SKU o producto ya existe. Por favor verifica los datos.',
                confirmButtonText: 'Aceptar',
                customClass: {
                    confirmButton: 'bg-fuchsia text-white border-0 px-4 py-2 rounded',
                    title: 'text-fuchsia',
                }
            });
        } else {
            // Otro error
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Ocurrió un error al guardar el producto. Intenta nuevamente.',
                confirmButtonText: 'Aceptar',
                customClass: {
                    confirmButton: 'bg-fuchsia text-white border-0 px-4 py-2 rounded',
                    title: 'text-fuchsia',
                }
            });
        }
    } catch (error) {
        console.error(error);
        Swal.fire({
            icon: 'error',
            title: 'Error de conexión',
            text: 'No se pudo conectar con el servidor. Intenta más tarde.',
            confirmButtonText: 'Aceptar',
            customClass: {
                confirmButton: 'bg-fuchsia text-white border-0 px-4 py-2 rounded',
                title: 'text-fuchsia',
            }
        });
    }
};


async function getFormData() {
    const dimensionesInputs = document.querySelectorAll("#dimensions-new-product input");

    const inputImagenes = document.getElementById("image-new-product");
    let imageBase64 = null;

    if (inputImagenes && inputImagenes.files.length > 0) {
        const file = inputImagenes.files[0];
        imageBase64 = await new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => {
                // Eliminamos el prefijo "data:image/xxx;base64,"
                const base64String = reader.result.split(",")[1];
                resolve(base64String);
            };
            reader.onerror = reject;
            reader.readAsDataURL(file);
        });
    }

    const countrySelect = document.getElementById("country-new-product");
    const countryValue = countrySelect.value.trim();
    const nameCountrySelect = countryValue ? countrySelect.selectedOptions[0].textContent : null;

    const stateSelect = document.getElementById("state-new-product");
    const stateValue = stateSelect.value.trim();
    const nameStateSelect = stateValue ? stateSelect.selectedOptions[0].textContent : null;

    const categorySelect = document.getElementById("category-new-product");
    const selectedOptionCategorySelect = categorySelect.selectedOptions[0];
    const nameCategorySelect = selectedOptionCategorySelect ? selectedOptionCategorySelect.dataset.name : null;

    const salesFormatSelect = document.getElementById("price-format-new-product");
    const selectedOptionSalesFormatSelect = salesFormatSelect.selectedOptions[0];
    const nameSalesFormatSelect = selectedOptionSalesFormatSelect ? selectedOptionSalesFormatSelect.dataset.name : null;

    return {
        productName: document.getElementById("name-new-product").value.trim(),
        sku: document.getElementById("sku-new-product").value.trim(),
        image: imageBase64,
        picture: null,
        description: document.getElementById("description-new-product").value.trim(),
        price: parseFloat(document.getElementById("price-new-product").value) || 0,
        productSalesFormatId: salesFormatSelect.value ? parseInt(salesFormatSelect.value, 10) : null,
        productSalesFormatValue: nameSalesFormatSelect || null,
        supplierCost: null,
        quantityStock: parseInt(document.getElementById("stock-new-product").value, 10) || 0,
        weight: parseFloat(document.getElementById("weight-new-product")?.value) || 0,
        length: parseFloat(document.getElementById("length-product")?.value) || 0,
        width: parseFloat(document.getElementById("width-product")?.value) || 0,
        height: parseFloat(document.getElementById("height-product")?.value) || 0,
        status: parseInt(document.getElementById("stock-new-product").value, 10) > 0 ? true : false,
        lowStockThreshold: parseInt(document.getElementById("stock-threshold")?.value) || 0,
        allowBackorders: parseInt(document.getElementById("allow-reserve")?.value, 10) || false,
        averageRating: 5.0,
        discountType: document.getElementById("discount-type-new-product")?.value || "percentage",
        discountValue: parseFloat(document.getElementById("price-discount-new-product")?.value) || 0,
        productCountryId: countryValue ? parseInt(countryValue, 10) : null,
        productCountryValue: nameCountrySelect,
        productStateId: stateValue ? parseInt(stateValue, 10) : null,
        productStateValue: nameStateSelect,
        productCategoryId: categorySelect.value ? parseInt(categorySelect.value, 10) : null,
        productCategoryValue: categorySelect.value ? nameCategorySelect : null
    };
}


const imageInput = document.getElementById("image-new-product");
let selectedFile = null;

imageInput.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (file) {
        selectedFile = file;
    } else {
        selectedFile = null;
    }
});


const precioNormalInput = document.getElementById("price-new-product");
const descuentoInput = document.getElementById("discount-new-product");
const precioOfertaInput = document.getElementById("price-discount-new-product");

precioNormalInput.addEventListener("input", () => {
    if (precioNormalInput.value.trim() !== "") {
        priceChange(); // Verifica si hay descuento o precio de oferta y actualiza el otro
    }
});

descuentoInput.addEventListener("input", () => {
    if (descuentoInput.value.trim() !== "") {
        // Si hay descuento, calcula precio oferta y deshabilita precio oferta
        princingDiscountChange();
        precioOfertaInput.disabled = true;
    } else {
        // Si descuento está vacío, habilita precio oferta y limpia su valor
        precioOfertaInput.disabled = false;
        precioOfertaInput.value = "";
    }
});

precioOfertaInput.addEventListener("input", () => {
    if (precioOfertaInput.value.trim() !== "") {
        // Si hay precio oferta, calcula descuento y deshabilita descuento
        discountChange();
        descuentoInput.disabled = true;
    } else {
        // Si precio oferta está vacío, habilita descuento y limpia su valor
        descuentoInput.disabled = false;
        descuentoInput.value = "";
    }
});

const priceChange = () => {
    const price = parseFloat(precioNormalInput.value);
    const discount = parseFloat(descuentoInput.value);
    const offerPrice = parseFloat(precioOfertaInput.value);

    const discountFilled = descuentoInput.value.trim() !== "" && !isNaN(discount);
    const offerPriceFilled = precioOfertaInput.value.trim() !== "" && !isNaN(offerPrice);

    if (!isNaN(price)) {
        if (discountFilled) {
            const newOfferPrice = price - (price * (discount / 100));
            precioOfertaInput.value = newOfferPrice.toFixed(2);
        } else if (offerPriceFilled) {
            const newDiscount = ((price - offerPrice) / price) * 100;
            descuentoInput.value = newDiscount.toFixed(2);
        }
    }
};

function princingDiscountChange() {
    const precioNormal = parseFloat(precioNormalInput.value);
    const descuento = parseFloat(descuentoInput.value);

    if (!isNaN(precioNormal) && !isNaN(descuento)) {
        const precioOferta = precioNormal - (precioNormal * (descuento / 100));
        precioOfertaInput.value = precioOferta.toFixed(2);
    } else {
        precioOfertaInput.value = "";
    }
}

function discountChange() {
    const precioNormal = parseFloat(precioNormalInput.value);
    const precioOferta = parseFloat(precioOfertaInput.value);

    if (!isNaN(precioNormal) && !isNaN(precioOferta) && precioNormal > 0) {
        const descuento = ((precioNormal - precioOferta) / precioNormal) * 100;
        descuentoInput.value = descuento.toFixed(2);
    } else {
        descuentoInput.value = "";
    }
}


function validateFormFields({ showAlert = false } = {}) {
    const producto = getFormData();

    const camposRequeridos = [
        { id: "name-new-product", nombre: "Nombre" },
        { id: "description-new-product", nombre: "Descripción" },
        { id: "price-new-product", nombre: "Precio", min: 0.01 },
        { id: "price-format-new-product", nombre: "Formato de venta" },
        { id: "category-new-product", nombre: "Categoría" },
        { id: "country-new-product", nombre: "País" },
        { id: "sku-new-product", nombre: "SKU" },
        { id: "stock-new-product", nombre: "Stock", min: 1 }
    ];

    if (producto.country === "México") {
        camposRequeridos.push({ id: "state-new-product", nombre: "Estado" });
    }

    let valido = true;
    let primerCampoInvalido = null;

    camposRequeridos.forEach(campo => {
        const input = document.getElementById(campo.id);
        const valor = input.value.trim();
        const esNumero = !isNaN(valor) && valor !== "";

        let invalido = false;

        // Vacío
        if (!valor) invalido = true;

        // Numérico y con mínimo definido
        if (esNumero && campo.min !== undefined && Number(valor) < campo.min) {
            invalido = true;
        }

        if (invalido) {
            input.classList.add("is-invalid");
            input.classList.remove("is-valid");
            if (!primerCampoInvalido) primerCampoInvalido = input;
            valido = false;
        } else {
            input.classList.remove("is-invalid");
            input.classList.add("is-valid");
        }
    });

    if (!valido && showAlert) {
        primerCampoInvalido.scrollIntoView({ behavior: 'smooth', block: 'center' });
        primerCampoInvalido.focus();

        Swal.fire({
            icon: 'warning',
            title: 'Campos inválidos',
            text: 'Por favor revisa los campos. No se permiten valores vacíos ni 0.',
            confirmButtonText: 'Revisar',
            customClass: {
                confirmButton: 'bg-fuchsia text-white border-0 px-4 py-2 rounded',
                icon: 'bg-fuchsia text-white p-3 rounded-circle border-0',
                title: 'text-fuchsia',
            }
        });

        return false;
    }

    return valido;
}



const inputImagen = document.getElementById("image-new-product");
const btnEliminarImagen = document.getElementById("remove-image-btn");

inputImagen.addEventListener("change", () => {
    if (inputImagen.files.length > 0) {
        btnEliminarImagen.classList.remove("d-none");
    } else {
        btnEliminarImagen.classList.add("d-none");
    }
});

btnEliminarImagen.addEventListener("click", () => {
    inputImagen.value = ""; // limpia el input
    btnEliminarImagen.classList.add("d-none"); // oculta el botón
});