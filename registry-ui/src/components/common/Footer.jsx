const Footer = () => {
  return (
    <div>
      <footer className="mt-8 py-4">
        <div className="max-w-sm mx-auto text-center">
          <p className="text-sm text-gray-500">
            &copy; {new Date().getFullYear} Your Company Name. All rights
            reserved.
          </p>
        </div>
      </footer>
    </div>
  );
};

export default Footer;
